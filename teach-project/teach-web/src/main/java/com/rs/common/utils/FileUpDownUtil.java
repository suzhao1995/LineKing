package com.rs.common.utils;

import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.service.studyAttr.TestAndWorkService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * FileUpDownUtil.java
 *
 * @Description:文件上传下载工具类
 * @author: suzhao
 * @date: 2019年8月1日 上午11:40:20
 * @version: V1.0
 */
@Component
public class FileUpDownUtil {

    private static final List<String> upLoadType = new ArrayList<String>();    //指定上传文件格式
    private static final List<String> upLoadPicType = new ArrayList<String>();    //指定上传文件格式
    private static final List<String> uploadMaterielType = new ArrayList<String>();    //指定物料上传格式
    private static final List<String> uploadVideoType = new ArrayList<String>();    //指定视频上传格式
    private static final List<String> uploadTrainDataType = new ArrayList<String>();    //指定培训考核上传格式
    private static Logger logger = Logger.getLogger(FileUpDownUtil.class);

    private static String filePath;    //文件路径
    private static String imgPath;    //图片路径
    private static String materielPath;    //物料路径
    private static String videoPath;    //视频路径
    private static String trainDataPath;    //培训考核文件路径
    @Value("${filePath}")    //静态属性使用setter方法注入properties文件的属性
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Value("${picPath}")
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Value("${materielPath}")
    public void setMaterielPath(String materielPath) {
        this.materielPath = materielPath;
    }

    @Value("${videoPath}")    //静态属性使用setter方法注入properties文件的属性
    public void setVideoPath(String videoPath) {
        FileUpDownUtil.videoPath = videoPath;
    }

    @Value("${trainDataPath}")    //静态属性使用setter方法注入properties文件的属性
    public void setTrainDataPath(String trainDataPath) {
        FileUpDownUtil.trainDataPath = trainDataPath;
    }


    private static String imgSaveUrl;    //图像映射路径
    private static String materielSaveUrl;    //物料映射路径
    private static String fileSaveUrl;    //文件映射路径
    private static String videoSaveUrl;    //视频映射路径
    private static String trainDataSaveUrl;    //培训考核文件映射路径
    @Value("${imgMappingPath}")    //静态属性使用setter方法注入properties文件的属性
    public void setImgSaveUrl(String imgSaveUrl) {
        this.imgSaveUrl = imgSaveUrl;
    }
    @Value("${materMappingPath}")    //静态属性使用setter方法注入properties文件的属性
    public void setMaterielSaveUrl(String materielSaveUrl) {
        this.materielSaveUrl = materielSaveUrl;
    }
    @Value("${fileMappingPath}")    //静态属性使用setter方法注入properties文件的属性
    public void setFileSaveUrl(String fileSaveUrl) {
        this.fileSaveUrl = fileSaveUrl;
    }
    @Value("${videoMappingUrl}")    //静态属性使用setter方法注入properties文件的属性
    public void setVideoSaveUrl(String videoSaveUrl) {
        FileUpDownUtil.videoSaveUrl = videoSaveUrl;
    }
    @Value("${trainDataMappingPath}")    //静态属性使用setter方法注入properties文件的属性
    public void setTrainDataSaveUrl(String trainDataSaveUrl) {
        FileUpDownUtil.trainDataSaveUrl = trainDataSaveUrl;
    }


    /**
     * testAndWorkService注入
     */
    @Autowired
    private TestAndWorkService testAndWorkService;

    private static FileUpDownUtil fileUpDownUtil;

    @PostConstruct
    public void init() {
        fileUpDownUtil = this;
        fileUpDownUtil.testAndWorkService = this.testAndWorkService;
    }

    /**
     * 静态代码块
     */
    static {
        //图片格式
        upLoadPicType.add(".jpg");
        upLoadPicType.add(".png");
        //文件格式
        upLoadType.add(".ppt");
        upLoadType.add(".pdf");
        //物料格式
        uploadMaterielType.add(".jpg");
        uploadMaterielType.add(".png");
        uploadMaterielType.add(".ppt");
        uploadMaterielType.add(".pdf");
        //视频格式
        uploadVideoType.add(".mp4");
        //培训考核文件格式
        uploadTrainDataType.add(".doc");
        uploadTrainDataType.add(".docx");
    }

    /**
     * 物料上传 表单指定enctype="multipart/form-data"
     *
     * @param
     * @return Map<String, Object>
     * @throws
     * @author suzhao
     * @date 2019年8月1日 下午4:17:25
     */
    public static Map<String, Object> materielUpLoad(HttpServletRequest request, MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //文件保存路径
        //String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/img");	//保存的文件根目录
        String savePath = materielPath;
        //发生异常删除文件的路径
        String catchFilePath = null;
        if (!file.isEmpty()) {
            try {
                String upLoadId = UUID.randomUUID().toString().replace("-", "");//生成章节id

                String updateFileName = file.getOriginalFilename().split("[.]")[0];
                String sectionType = "." + file.getOriginalFilename().split("[.]")[1];
                if (!uploadMaterielType.contains(sectionType)) {
                    resultMap.put("code", "-1");
                    resultMap.put("message", "上传文件格式错误！");
                    return resultMap;
                }
                //使用hash算法散列存储文件位置
                Map<String, String> dirPathMap = findFileSavePathByFileName(updateFileName, savePath);
                String dirPath = dirPathMap.get("dir");
                String saveRealName = upLoadId + sectionType;
                
                catchFilePath = dirPath + "\\" + saveRealName;
                
                file.transferTo(new File(dirPath + "\\" + saveRealName));

				String materielPath = materielSaveUrl + dirPathMap.get("sortDir") +"/"+ saveRealName;

                resultMap.put("materielUrl", dirPath + "\\" + saveRealName);    //本地服务器地址
                resultMap.put("materielPath", materielPath);    //绝对路径
                resultMap.put("materielId", upLoadId);    //生成的随机ID，唯一
                resultMap.put("code", "0");
                resultMap.put("message", "文件上传成功");

            } catch (Exception e) {
                resultMap.put("code", "-1");
                resultMap.put("message", "文件上传异常");
                logger.error("---------文件上传异常---------", e);
                //删除原始文件
                File officeFile = new File(catchFilePath);
                if (officeFile.exists()) {
                    //删除
                    officeFile.delete();
                }
            }

        } else {
            resultMap.put("code", "-1");
            resultMap.put("message", "上传文件为空");
        }
        return resultMap;
    }

    /**
     * 图片上传 表单指定enctype="multipart/form-data"
     *
	* @param
     * @param
     * @return Map<String, Object>
     * @throws
     * @author suzhao
     * @date 2019年8月1日 下午4:17:25
     */
    public static Map<String, Object> picUpLoad(HttpServletRequest request, MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //文件保存路径
        //String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/img");	//保存的文件根目录
        String savePath = imgPath;
        //发生异常删除文件的路径
        String catchFilePath = null;
        if (!file.isEmpty()) {
            try {
                String upLoadId = UUID.randomUUID().toString().replace("-", "");//生成章节id

                String updateFileName = file.getOriginalFilename().split("[.]")[0];
                String sectionType = "." + file.getOriginalFilename().split("[.]")[1];
                if (!upLoadPicType.contains(sectionType)) {
                    resultMap.put("code", "-1");
                    resultMap.put("message", "上传文件格式错误！");
                    return resultMap;
                }
                //使用hash算法散列存储文件位置
                Map<String, String> dirPathMap = findFileSavePathByFileName(updateFileName, savePath);
                String dirPath = dirPathMap.get("dir");
                String saveRealName = upLoadId + sectionType;
                
                catchFilePath = dirPath + "\\" + saveRealName;
                
                file.transferTo(new File(dirPath + "\\" + saveRealName));

                String picUrl = imgSaveUrl + dirPathMap.get("sortDir") + "/" + saveRealName;

                resultMap.put("picUrl", picUrl);	//映射路径
                resultMap.put("saveUrl", dirPath + "\\" + saveRealName);	//保存在服务器的路径
                resultMap.put("picId", upLoadId);    //生成的随机章节ID，唯一
                resultMap.put("code", "0");
                resultMap.put("message", "文件上传成功");

            } catch (Exception e) {
                resultMap.put("code", "-1");
                resultMap.put("message", "文件上传异常");
                logger.error("---------文件上传异常---------", e);
                
                //删除原始文件
                File officeFile = new File(catchFilePath);
                if (officeFile.exists()) {
                    //删除
                    officeFile.delete();
                }
            }

        } else {
            resultMap.put("code", "-1");
            resultMap.put("message", "上传文件为空");
        }
        return resultMap;
    }

    /**
     * 课件上传方法 表单指定enctype="multipart/form-data"
     *
     * @param
     * @return Map<String, Object>
     * @throws
     * @author suzhao
     * @date 2019年8月1日 下午4:17:25
     */
    public static Map<String, Object> fileUpLoad(HttpServletRequest request, MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //String courseId = request.getParameter("courseId");	//课程资源ID
        //文件保存路径
        //String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");	//保存的文件根目录
        String savePath = filePath;
        //发生异常删除文件的路径
        String catchFilePath = null;
        if (!file.isEmpty()) {
            try {
                String upLoadId = UUID.randomUUID().toString().replace("-", "");//生成章节id

                String updateFileName = file.getOriginalFilename().split("[.]")[0];
                String sectionType = "." + file.getOriginalFilename().split("[.]")[1];
                if (!upLoadType.contains(sectionType)) {
                    resultMap.put("code", "-1");
                    resultMap.put("message", "上传文件格式错误！");
                    return resultMap;
                }
                //使用hash算法散列存储文件位置
                Map<String, String> dirPathMap = findFileSavePathByFileName(updateFileName, savePath);
                String dirPath = dirPathMap.get("dir");
                String saveRealName = upLoadId + "_" + file.getOriginalFilename();

                catchFilePath = dirPath + "\\" + saveRealName;

                file.transferTo(new File(dirPath + "\\" + saveRealName));

                String officeUrl = dirPath + "\\" + saveRealName;
                String pdfUrl = dirPath + "\\" + upLoadId + "_" + updateFileName + ".pdf";
                //调用pdf转换类
                if (!".pdf".equals(sectionType)) {

                    Map<String, Object> officeMap = Office2PdfUtil.Word2Pdf(officeUrl, pdfUrl);
                    if ("-1".equals(officeMap.get("resultCode"))) {
                        resultMap.put("code", "-1");
                        resultMap.put("message", "文件上传异常");
                        //删除原始文件
                        File officeFile = new File(catchFilePath);
                        if (officeFile.exists()) {
                            //删除
                            officeFile.delete();
                        }
                        logger.error("---------文件上传异常---------");
                        return resultMap;
                    }
                }
				String fileMappingPath = fileSaveUrl + dirPathMap.get("sortDir") + "/" + upLoadId+"_"+updateFileName+".pdf";
                resultMap.put("upLoadId", upLoadId);    //生成的随机章节ID，唯一	上传为章节课件 upLoadId = courseWareId， 上传为修改课件， upLoadId = upLoadId
                resultMap.put("updateFileName", updateFileName);
                resultMap.put("sectionUrl", dirPathMap.get("sortDir"));
                resultMap.put("pdfUrl", pdfUrl);    //完整的pdf保存路径
                resultMap.put("catchFilePath",catchFilePath);  //原始文件保存路径
                resultMap.put("fileMappingPath", fileMappingPath);    //映射路径
                resultMap.put("sectionType", sectionType);
                resultMap.put("code", "0");
                resultMap.put("message", "文件上传成功");

            } catch (Exception e) {
                resultMap.put("code", "-1");
                resultMap.put("message", "文件上传异常");
                logger.error("---------文件上传异常---------", e);
                //删除原始文件
                File officeFile = new File(catchFilePath);
                if (officeFile.exists()) {
                    //删除
                    officeFile.delete();
                }
            }

        } else {
            resultMap.put("code", "-1");
            resultMap.put("message", "上传文件为空");
        }
        return resultMap;
    }

    /**
     * 培训考核文件上传方法 表单指定enctype="multipart/form-data"
     *
     * @param
     * @return Map<String, Object>
     * @throws
     * @author suzhao
     * @date 2019年8月1日 下午4:17:25
     */
    public static Map<String, Object> trainDataUpload(MultipartFile file) {
        HashMap<String, Object> resultMap = new HashMap<>();
        String savePath = trainDataPath;
        //发生异常删除文件的路径
        String catchFilePath = null;

        if (!file.isEmpty()) {
            try {
                String trainDataId = UUID.randomUUID().toString().replace("-", "");//生成文件id

                String trainDataFileName = file.getOriginalFilename().split("[.]")[0];
                String trainDataType = "." + file.getOriginalFilename().split("[.]")[1];
                if (!uploadTrainDataType.contains(trainDataType)) {
                    resultMap.put("code", "-1");
                    resultMap.put("message", "上传文件格式错误！");
                    return resultMap;
                }
                //使用hash算法散列存储文件位置
                Map<String, String> dirPathMap = findFileSavePathByFileName(trainDataFileName, savePath);
                String dirPath = dirPathMap.get("dir");
                String saveRealName = trainDataId + trainDataType;

                catchFilePath = dirPath + "\\" + saveRealName;

                file.transferTo(new File(dirPath + "\\" + saveRealName));

                String trainDataPath = trainDataSaveUrl + dirPathMap.get("sortDir") + "/" + saveRealName;
                String trainDataUrl = dirPath + "\\" + saveRealName;

                resultMap.put("trainDataId", trainDataId);    //生成的文件ID
                resultMap.put("trainDataFileName",trainDataFileName); //上传文件名
                resultMap.put("trainDataPath", trainDataPath);  //项目路径
                resultMap.put("trainDataUrl", trainDataUrl);   //本地服务器地址
                resultMap.put("trainDataType", trainDataType);    //文件格式类型
                resultMap.put("code", "0");
                resultMap.put("message", "文件上传成功");

            } catch (IllegalStateException e) {
                resultMap.put("code", "-1");
                resultMap.put("message", "文件上传异常");
                logger.error("---------文件上传异常---------", e);
            } catch (Exception e) {
                resultMap.put("code", "-1");
                resultMap.put("message", "文件上传异常");
                logger.error("---------文件上传异常---------", e);
                //删除原始文件
                File officeFile = new File(catchFilePath);
                if (officeFile.exists()) {
                    //删除
                    officeFile.delete();
                }
            }

        } else {
            resultMap.put("code", "-1");
            resultMap.put("message", "上传文件为空");
        }
        return resultMap;
    }



    /**
     * 视频上传方法 表单指定enctype="multipart/form-data"
     *
     * @param
     * @return Map<String, Object>
     * @throws
     * @author suzhao
     * @date 2019年8月1日 下午4:17:25
     */
    public static Map<String, Object> videoUpload(MultipartFile file) {
        HashMap<String, Object> resultMap = new HashMap<>();
        String savePath = videoPath;
        //发生异常删除文件的路径
        String catchFilePath = null;

        if (!file.isEmpty()) {
            try {
                String upLoadId = UUID.randomUUID().toString().replace("-", "");//生成文件id

                String updateFileName = file.getOriginalFilename().split("[.]")[0];
                String sectionType = "." + file.getOriginalFilename().split("[.]")[1];
                if (!uploadVideoType.contains(sectionType)) {
                    resultMap.put("code", "-1");
                    resultMap.put("message", "上传文件格式错误！");
                    return resultMap;
                }
                //使用hash算法散列存储文件位置
                Map<String, String> dirPathMap = findFileSavePathByFileName(updateFileName, savePath);
                String dirPath = dirPathMap.get("dir");
                String saveRealName = upLoadId + sectionType;

                catchFilePath = dirPath + "\\" + saveRealName;

                file.transferTo(new File(dirPath + "\\" + saveRealName));

                String videoPath = videoSaveUrl + dirPathMap.get("sortDir") + "/" + saveRealName;
                String videoUrl = dirPath + "\\" + saveRealName;

                resultMap.put("videoUrl", videoUrl);   //本地服务器地址
                resultMap.put("updateFileName",updateFileName); //上传文件名
                resultMap.put("videoPath", videoPath);  //项目绝对路径
                resultMap.put("videoId", upLoadId);    //生成的文件ID
                resultMap.put("code", "0");
                resultMap.put("message", "文件上传成功");

            }catch (Exception e) {
                resultMap.put("code", "-1");
                resultMap.put("message", "文件上传异常");
                logger.error("---------文件上传异常---------", e);
                //删除原始文件
                File officeFile = new File(catchFilePath);
                if (officeFile.exists()) {
                    //删除
                    officeFile.delete();
                }
            }

        } else {
            resultMap.put("code", "-1");
            resultMap.put("message", "上传文件为空");
        }
        return resultMap;
    }


    /**
     * 视频下载方法
     *
     * @param filePath   文件全路径 例如：D:\RSUpLoad\4\3\486e9d53ae764cb6b733e375f90fce22_电信（第三版）.MP4
     * @param downLoadName   文件保存名 例如：电信（第三版）
     * @return void
     * @throws
     * @throws IOException
     * @author suzhao
     * @date 2019年8月1日 上午11:46:24
     */
    public static Map<String, Object> videoFileDownLoad(HttpServletResponse response, String filePath, String downLoadName ) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            resultMap.put("code", "-1");
            resultMap.put("message", "您想要下载的资源不存在或已被删除！");
            return resultMap;
        }
        //设置响应头，控制浏览器下载该文件
        response.setContentType("video/mpeg4");// 设定输出的类型
        response.setHeader("Content-Disposition", "attachment;filename="  + URLEncoder.encode(downLoadName+".mp4","UTF-8"));
        //读取文件，保存文件到输入流
        FileInputStream inputStream = new FileInputStream(file);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte[] buffer = new byte[1024];
        int len = inputStream.read(buffer);
        //将文件读入缓冲区
        while (len > 0) {
            //输出缓冲区的内容到浏览器
            out.write(buffer, 0, len);
        }

        resultMap.put("code", "0");
        resultMap.put("message", "成功");
        //关闭资源
        inputStream.close();
        out.close();
        return resultMap;
    }


    /**
     * 文件下载方法
     *
     * @param filePath     文件全路径
     * @param sectionType  保存文件的文档类型
     * @param downLoadName 下载的文件名称
     * @return void
     * @throws
     * @throws IOException
     * @author suzhao
     * @date 2019年8月1日 上午11:46:24
     */
    public static Map<String, Object> fileDownLoad(HttpServletRequest request, HttpServletResponse response, String filePath, String sectionType, String downLoadName) throws IOException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //上传文件时，设置文件的随机id为文件名

//		String fileName = sectionId +"_"+ upLoadName;	//保存在服务器文件的名称
        //保存文件的地址
        //String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
//		String savePath = filePath;
//		savePath = savePath + sectionUrl.replace("/", "\\");

//		String fileRealPath = savePath + "\\" + fileName + sectionType;
        File file = new File(filePath);
        if (!file.exists()) {
            resultMap.put("code", "-1");
            resultMap.put("message", "您想要下载的资源不存在或已被删除！");
            return resultMap;
        }
        //设置响应头，控制浏览器下载该文件
        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downLoadName + sectionType, "UTF-8"));
        //读取文件，保存文件到输入流
        FileInputStream inputStream = new FileInputStream(file);
        //创建输出流
        OutputStream out = response.getOutputStream();
        //创建缓冲区
        byte[] buffer = new byte[1024];
        int len = 0;
        //将文件读入缓冲区
        while ((len = inputStream.read(buffer)) > 0) {
            //输出缓冲区的内容到浏览器
            out.write(buffer, 0, len);
        }

        resultMap.put("code", "0");
        resultMap.put("message", "成功");
        //关闭资源
        inputStream.close();
        out.close();
        return resultMap;
    }


    /**
     * 下载当前课程所有文件
     *
     * @param sections（当前课程所有章节信息）
     * @param temporaryPath（服务器端创建文件临时储存总目录）
     */
    public static void allDownLoadUtil(List<Section> sections, String temporaryPath) {
        //存储文件的根目录
        String savePath = filePath;

        for (Section section : sections) {
            if (StringUtils.isEmpty(section.getSectionUrl())) {
                continue;
            }
            String fileRealPath = null;
            if(section.getSectionUrl().endsWith("mp4")){
            	//视频课件下载
            	fileRealPath = section.getSectionUrl();
            }else{
            	//普通课件下载
            	String filePath = savePath + section.getSectionUrl().replace("/", "\\");
            	fileRealPath = filePath + "\\" + section.getCoursewareId() + "_" + section.getUpdateFileName() + section.getSectionType();
            }
            File file = new File(fileRealPath);
            if (file.exists()) {
                //服务器端创建文件临时储存二级目录
                String secondTemPath = temporaryPath + "\\" + section.getSectionSort();
                File secondTemPathFile = new File(secondTemPath);
                if (!secondTemPathFile.exists()) {    //如果不存在目录就创建目录
                    secondTemPathFile.mkdirs();
                }
                //复制文件到指定二级目录
                String toFilePath = secondTemPath + "\\" + section.getCoursewareId() + "_" + section.getUpdateFileName() + section.getSectionType();
                File toFile = new File(toFilePath);
                try {
                    Files.copy(file.toPath(), toFile.toPath());
                } catch (IOException e) {
                    logger.error("------复制文件异常------" + file.getName(), e);
                }
                //查询练习信息
                if (StringUtils.isNotEmpty(section.getWorkId())) {
                    Practice work = fileUpDownUtil.testAndWorkService.getPracticeById(section.getWorkId());
                    File workFile = new File(work.getPracticeUrl());    //源文件
                    if (workFile.exists()) {
                        String[] workUrls = work.getPracticeUrl().split("\\\\");    //反斜杠切割
                        String workFileName = workUrls[workUrls.length - 1];
                        String workToPath = secondTemPath + "\\" + workFileName;
                        File workToFile = new File(workToPath);
                        try {
                            Files.copy(workFile.toPath(), workToFile.toPath());
                        } catch (IOException e) {
                            logger.error("------复制文件异常------" + workFile.getName(), e);
                        }
                    }
                }
                //查询试卷信息
                if (StringUtils.isNotEmpty(section.getTestPaperId())) {
                    Testpaper testPaper = fileUpDownUtil.testAndWorkService.getTestpaper(section.getTestPaperId());
                    File testFile = new File(testPaper.getTestpaperUrl());    //源文件
                    if (testFile.exists()) {
                        String[] testUrls = testPaper.getTestpaperUrl().split("\\\\");    //反斜杠切割
                        String testFileName = testUrls[testUrls.length - 1];
                        String testToPath = secondTemPath + "\\" + testFileName;
                        File testToFile = new File(testToPath);
                        try {
                            Files.copy(testFile.toPath(), testToFile.toPath());
                        } catch (IOException e) {
                            logger.error("------复制文件异常------" + testFile.getName(), e);
                        }
                    }
                }
            }

        }
    }

    /**
     * @param filename     要下载的文件名
     * @param saveRootPath 上传文件保存的根目录
     * @return 要下载的文件的存储目录
     * @Method: findFileSavePathByFileName
     * @Description: 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
     * @Anthor:suzhao
     */
    public static Map<String, String> findFileSavePathByFileName(String filename, String saveRootPath) {
        Map<String, String> pathMap = new HashMap<String, String>();

        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf;  //0--15
        int dir2 = (hashcode & 0xf0) >> 4;  //0-15
        String dir = saveRootPath + "\\" + dir1 + "\\" + dir2;
        String sortDir = "/" + dir1 + "/" + dir2;
        pathMap.put("dir", dir);
        pathMap.put("sortDir", sortDir);

        File file = new File(dir);
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        return pathMap;
    }

    public static void main(String[] args) {
        String name = "密码资料.txt";
        String s1 = name.split("[.]")[0];
        System.out.println(s1);
    }


}