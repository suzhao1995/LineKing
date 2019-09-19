package com.rs.teach.controller.backstage;

import cn.hutool.core.util.StrUtil;
import com.rs.common.utils.BPUtil;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.common.utils.ZipUtil;
import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.dto.DownloadSectionDto;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.dto.TotleSectionDto;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.section.SectionService;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.studyAttr.TestAndWorkService;
import com.rs.teach.service.training.TrainCourseService;
import com.rs.teach.service.training.TrainSectionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * @author 汪航
 * @Description 后台管理员章节controller
 * @create 2019-08-15 11:07
 */
@Controller
@RequestMapping("/section")
public class SectionController {

    private static final Logger logger = Logger.getLogger(SectionController.class);

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TrainSectionService trainSectionService;

    @Autowired
    private TestAndWorkService testAndWorkService;

    @Autowired
    private TrainCourseService trainCourseService;

    @Autowired
    private CourseService courseService;

    @Value("${filePath}")
    private String filePath;    //文件存放根目录


    /**
     * 添加大章节
     *
     * @param totleSectionDto
     * @return
     */
    @RequestMapping(value = "/addTotleSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addTotleSection(@RequestBody TotleSectionDto totleSectionDto) {
        ResponseBean bean = new ResponseBean();
        try {
            BPUtil.check(StrUtil.isEmpty(totleSectionDto.getCourseId()), "请选择课程");
            if (StrUtil.equals("1", totleSectionDto.getIsTrain())) {
                trainSectionService.addTotleSection(totleSectionDto);
            } else {
                sectionService.addTotleSection(totleSectionDto);
            }
            bean.addSuccess();
            logger.info("大章节-添加-成功");
        } catch (Exception e) {
            logger.error("大章节-添加-失败");
            bean.addError("大章节添加失败");
        }
        return bean;
    }

    /**
     * 查询大章节
     *
     * @param totleSectionDto
     * @return
     */
    @RequestMapping(value = "/selectTotleSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectTotleSection(@RequestBody TotleSectionDto totleSectionDto) {
        ResponseBean bean = new ResponseBean();
        List<TotleSection> totleSectionList = null;
        if (StrUtil.equals("1", totleSectionDto.getIsTrain())) {
            totleSectionList = trainSectionService.selectTotleSection(totleSectionDto);
        } else {
            totleSectionList = sectionService.selectTotleSection(totleSectionDto);
        }
        bean.addSuccess(totleSectionList);
        return bean;
    }

    /**
     * 修改大章节名
     *
     * @param totleSectionDto
     * @return
     */
    @RequestMapping(value = "/updateTotleSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateTotleSection(@RequestBody TotleSectionDto totleSectionDto) {
        ResponseBean bean = new ResponseBean();
        try {
            if (StrUtil.equals("1", totleSectionDto.getIsTrain())) {
                trainSectionService.updateTotleSection(totleSectionDto);
            } else {
                sectionService.updateTotleSection(totleSectionDto);
            }
            logger.info("修改-大章节-成功");
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("修改-大章节-失败", e);
            bean.addError("修改大章节失败");
        }
        return bean;
    }

    /**
     * 添加小章节
     *
     * @param sectionDto
     * @param request
     * @return
     */
    @RequestMapping(value = "/addSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addSection(@RequestParam(value = "courseWareFile" ,required = false) MultipartFile courseWareFile,
                                   @RequestParam(value = "practiceFile",required = false) MultipartFile practiceFile,
                                   @RequestParam(value = "testpaperFile",required = false) MultipartFile testpaperFile,
                                   SectionDto sectionDto,
                                   HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        Practice practice = new Practice();
        Testpaper testpaper = new Testpaper();
        /**
         * * @param courseWareFileName（课件）
         * * @param practiceFileName（作业）
         * * @param testpaperFileName（试卷）
         */
        // TODO 课件
        if (courseWareFile != null) {
            if (!courseWareFile.isEmpty()) {
                //课件文件上传
                Map<String, Object> courseWareMap = FileUpDownUtil.fileUpLoad(request, courseWareFile);
                //文件上传是否成功
                if (!(courseWareMap != null && "0".equals(courseWareMap.get("code")))) {
                    bean.addError(ResponseBean.CODE_FILE_ERROR, courseWareMap.get("message").toString());
                    logger.error(ResponseBean.CODE_FILE_ERROR);
                    return bean;
                }
                sectionDto.setCoursewareId(courseWareMap.get("upLoadId").toString());
                sectionDto.setLitterSectionUrl(courseWareMap.get(("sectionUrl")).toString());
                sectionDto.setUpdateFileName(courseWareMap.get("updateFileName").toString());
                sectionDto.setLitterSectionType(courseWareMap.get("sectionType").toString());

                sectionDto.setCoursewareUrl(courseWareMap.get("pdfUrl").toString());
            }
        }
        try {
            // TODO 作业
            if (practiceFile != null) {
                if (!courseWareFile.isEmpty()) {
                    //作业文件上传
                    Map<String, Object> practiceMap = FileUpDownUtil.fileUpLoad(request,practiceFile);
                    //文件上传是否成功
                    if (!(practiceMap != null && "0".equals(practiceMap.get("code")))) {
                        bean.addError(ResponseBean.CODE_FILE_ERROR, practiceMap.get("message").toString());
                        logger.error(ResponseBean.CODE_FILE_ERROR);
                        return bean;
                    }
                    practice.setPracticeId(practiceMap.get("upLoadId").toString());
                    practice.setPracticeFileName(practiceMap.get("updateFileName").toString());
                    practice.setPracticeUrl(practiceMap.get("pdfUrl").toString());
                    practice.setPracticePath(practiceMap.get("fileMappingPath").toString());

                    sectionDto.setPracticeId(practice.getPracticeId());

                    testAndWorkService.insertPractice(practice);
                }
            }
            // TODO 试卷
            if (testpaperFile != null) {
                if (!courseWareFile.isEmpty()) {
                    //试卷文件上传
                    Map<String, Object> testpaperMap = FileUpDownUtil.fileUpLoad(request, testpaperFile);
                    //文件上传是否成功
                    if (!(testpaperMap != null && "0".equals(testpaperMap.get("code")))) {
                        bean.addError(ResponseBean.CODE_FILE_ERROR, testpaperMap.get("message").toString());
                        logger.error(ResponseBean.CODE_FILE_ERROR);
                        return bean;
                    }
                    testpaper.setTestpaperId(testpaperMap.get("upLoadId").toString());
                    testpaper.setTestpaperName(testpaperMap.get("updateFileName").toString());
                    testpaper.setTestpaperUrl(testpaperMap.get("pdfUrl").toString());
                    testpaper.setTestpaperPath(testpaperMap.get("fileMappingPath").toString());

                    sectionDto.setTestpaperId(testpaper.getTestpaperId());

                    testAndWorkService.insertTestpaper(testpaper);
                }
            }
            if (StrUtil.equals("1", sectionDto.getIsTrain())) {
                trainSectionService.addTrainSection(sectionDto);
            } else {
                sectionService.addSection(sectionDto);
            }
            bean.addSuccess("添加成功！");
        } catch (Exception e) {
            logger.error("课程-添加-失败", e);
            //添加失败删除文件
            String[] fileNames = {sectionDto.getCoursewareUrl(), practice.getPracticeUrl(), testpaper.getTestpaperUrl()};
            DeleteFileUtil.deleteFiles(fileNames);
            bean.addError("-1", "添加失败");
        }
        return bean;
    }

    /**
     * 通过课程id查询大小章节信息
     *
     * @param sectionDto(只需要isTrain)
     * @return
     */
    @RequestMapping(value = "/selectSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectSection(@RequestBody SectionDto sectionDto) {
        ResponseBean bean = new ResponseBean();
        //返回结果集
        TrainCourseVo trainCourseVo;
        if (StrUtil.equals("1", sectionDto.getIsTrain())) {
            //单个课程以及含有的章节信息
            trainCourseVo = trainSectionService.selectCourseSection(sectionDto.getCourseId());
        } else {
            //单个课程以及含有的章节信息
            trainCourseVo = sectionService.selectCourseSection(sectionDto.getCourseId());
        }
        bean.addSuccess(trainCourseVo);
        return bean;
    }

    /**
     * 修改小章节
     *
     * @param sectionDto（）
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateSection(@RequestParam(value = "courseWareFile",required = false) MultipartFile courseWareFile,
                                      @RequestParam(value = "practiceFile",required = false) MultipartFile practiceFile,
                                      @RequestParam(value = "testpaperFile",required = false) MultipartFile testpaperFile,
                                      SectionDto sectionDto,
                                      HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        Practice practice = new Practice();
        Testpaper testpaper = new Testpaper();

        /**
         * * @param courseWareFileName（课件）
         * * @param practiceFileName（作业）
         * * @param testpaperFileName（试卷）
         */
        // TODO 课件
        if (courseWareFile != null) {
            if (!courseWareFile.isEmpty()){
                //课件文件上传
                Map<String, Object> courseWareMap = FileUpDownUtil.fileUpLoad(request, courseWareFile);
                //文件上传是否成功
                if (!(courseWareMap != null && "0".equals(courseWareMap.get("code")))) {
                    bean.addError(ResponseBean.CODE_FILE_ERROR, courseWareMap.get("message").toString());
                    return bean;
                }
                sectionDto.setCoursewareId(courseWareMap.get("upLoadId").toString());
                sectionDto.setLitterSectionUrl(courseWareMap.get(("sectionUrl")).toString());
                sectionDto.setUpdateFileName(courseWareMap.get("updateFileName").toString());
                sectionDto.setLitterSectionType(courseWareMap.get("sectionType").toString());

                sectionDto.setCoursewareUrl(courseWareMap.get("pdfUrl").toString());
            }
        }
        // TODO 作业
        if (practiceFile != null) {
            if (!practiceFile.isEmpty()){
                //作业文件上传
                Map<String, Object> practiceMap = FileUpDownUtil.fileUpLoad(request, practiceFile);
                //文件上传是否成功
                if (!(practiceMap != null && "0".equals(practiceMap.get("code")))) {
                    bean.addError(ResponseBean.CODE_FILE_ERROR, practiceMap.get("message").toString());
                    return bean;
                }
                practice.setPid(sectionDto.getPid());
                practice.setPracticeId(practiceMap.get("upLoadId").toString());
                practice.setPracticeFileName(practiceMap.get("updateFileName").toString());
                practice.setPracticeUrl(practiceMap.get("pdfUrl").toString());
                practice.setPracticePath(practiceMap.get("fileMappingPath").toString());

                sectionDto.setPracticeId(practice.getPracticeId());
            }
        }
        // TODO 试卷
        if (testpaperFile != null) {
            if (!testpaperFile.isEmpty()){
                //试卷文件上传
                Map<String, Object> testpaperMap = FileUpDownUtil.fileUpLoad(request, testpaperFile);
                //文件上传是否成功
                if (!(testpaperMap != null && "0".equals(testpaperMap.get("code")))) {
                    bean.addError(ResponseBean.CODE_FILE_ERROR, testpaperMap.get("message").toString());
                    return bean;
                }
                testpaper.setTid(sectionDto.getTid());
                testpaper.setTestpaperId(testpaperMap.get("upLoadId").toString());
                testpaper.setTestpaperName(testpaperMap.get("updateFileName").toString());
                testpaper.setTestpaperUrl(testpaperMap.get("pdfUrl").toString());
                testpaper.setTestpaperPath(testpaperMap.get("fileMappingPath").toString());

                sectionDto.setTestpaperId(testpaper.getTestpaperId());
            }
        }
        try {
            /** 作业 */
            if (StrUtil.isNotBlank(practice.getPid())) {
                //获取之前作业文件路径
                String practiceUrl = testAndWorkService.queryUrlByPid(sectionDto.getPid());
                testAndWorkService.updatePractice(practice);
                DeleteFileUtil.deleteFile(practiceUrl);
            } else if(StrUtil.isNotBlank(practice.getPracticeId())){
                testAndWorkService.insertPractice(practice);
            }
            /** 考试 */
            if (StrUtil.isNotBlank(testpaper.getTid())) {
                //获取之前考试文件路径
                String testpaperUrl = testAndWorkService.queryUrlByTid(sectionDto.getTid());
                testAndWorkService.updateTestpaper(testpaper);
                DeleteFileUtil.deleteFile(testpaperUrl);
            } else if(StrUtil.isNotBlank(testpaper.getTestpaperId())){
                testAndWorkService.insertTestpaper(testpaper);
            }

            if (StrUtil.equals("1", sectionDto.getIsTrain())) {
                //获取之前课件文件路径
                TrainSection trainSection = trainSectionService.selectTrainSection(sectionDto.getSectionId());
                trainSectionService.updateTrainSection(sectionDto);
                //修改成功再删除
                if (StrUtil.isNotBlank(trainSection.getCoursewareId())) {
                    //获取服务器路径（删除原始文件）
                    String coursewareUrl = filePath + trainSection.getTrainLitterSectionUrl().replace("/", "\\")
                            + "\\" + trainSection.getCoursewareId() + "_" + trainSection.getUpdateFileName();
                    DeleteFileUtil.deleteFile(coursewareUrl + trainSection.getTrainLitterSectionType());
                    if (!StrUtil.equalsIgnoreCase(".pdf", trainSection.getTrainLitterSectionType())) {
                        DeleteFileUtil.deleteFile(coursewareUrl + ".pdf");
                    }
                }
            } else {
                //获取之前课件文件路径
                Section section = sectionService.getSectionById(sectionDto.getSectionId());
                sectionService.updateSection(sectionDto);
                //修改成功再删除
                if (StrUtil.isNotBlank(section.getCoursewareId())) {
                    //获取服务器路径（删除原始文件）
                    String coursewareUrl = filePath + section.getSectionUrl().replace("/", "\\")
                            + "\\" + section.getCoursewareId() + "_" + section.getUpdateFileName();
                    DeleteFileUtil.deleteFile(coursewareUrl + section.getSectionType());
                    if (!StrUtil.equalsIgnoreCase(".pdf", section.getSectionType())) {
                        DeleteFileUtil.deleteFile(coursewareUrl + ".pdf");
                    }
                }
            }
            bean.addSuccess("修改成功！");
        } catch (Exception e) {
            logger.error("课程-修改-失败", e);
            //修改失败删除文件
            String[] fileNames = {sectionDto.getCoursewareUrl(), practice.getPracticeUrl(), testpaper.getTestpaperUrl()};
            DeleteFileUtil.deleteFiles(fileNames);
            bean.addError("-1", "修改失败");
        }
        return bean;
    }


    /**
     * 本节资源下载
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/resourceDownload", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean download(HttpServletRequest request, HttpServletResponse response) {
        ResponseBean bean = new ResponseBean();
        String isTrain = request.getParameter("isTrain");
        String sectionId = request.getParameter("sectionId");
        List<String> fileUrl = new ArrayList<String>();
        DownloadSectionDto downloadSectionDto = new DownloadSectionDto();

        if (StrUtil.equals("1", isTrain)) {
            //查询培训课程资源考试与练习
            TrainSection trainSection = trainSectionService.selectTrainSection(sectionId);
            downloadSectionDto.setSectionUrl(trainSection.getTrainLitterSectionUrl());
            downloadSectionDto.setCoursewareId(trainSection.getCoursewareId());
            downloadSectionDto.setUpdateFileName(trainSection.getUpdateFileName());
            downloadSectionDto.setSectionType(trainSection.getTrainLitterSectionType());
            downloadSectionDto.setWorkId(trainSection.getPracticeId());
            downloadSectionDto.setTestPaperId(trainSection.getTestpaperId());
            downloadSectionDto.setSectionName(trainSection.getTrainLitterSectionName());
        } else {
            //查询课程资源考试与练习
            Section section = sectionService.getSectionById(sectionId);
            downloadSectionDto.setSectionUrl(section.getSectionUrl());
            downloadSectionDto.setCoursewareId(section.getCoursewareId());
            downloadSectionDto.setUpdateFileName(section.getUpdateFileName());
            downloadSectionDto.setSectionType(section.getSectionType());
            downloadSectionDto.setWorkId(section.getWorkId());
            downloadSectionDto.setTestPaperId(section.getTestPaperId());
            downloadSectionDto.setSectionName(section.getSectionName());
        }

        if (StringUtils.isEmpty(downloadSectionDto.getSectionUrl())) {
            bean.addError("没有课件信息");
            return bean;
        }
        String savePath = filePath;
        String filePath = savePath + downloadSectionDto.getSectionUrl().replace("/", "\\");
        String fileRealPath = filePath + "\\" + downloadSectionDto.getCoursewareId() + "_" + downloadSectionDto.getUpdateFileName() + downloadSectionDto.getSectionType();
        fileUrl.add(fileRealPath);
        //查询练习信息
        if (StringUtils.isNotEmpty(downloadSectionDto.getWorkId())) {
            Practice work = testAndWorkService.getPracticeById(downloadSectionDto.getWorkId());
            fileUrl.add(work.getPracticeUrl());
        }
        //查询试卷信息
        if (StringUtils.isNotEmpty(downloadSectionDto.getTestPaperId())) {
            Testpaper testPaper = testAndWorkService.getTestpaper(downloadSectionDto.getTestPaperId());
            fileUrl.add(testPaper.getTestpaperUrl());
        }

        //打包文件
        List<File> files = new ArrayList<File>();
        for (String str : fileUrl) {
            File file = new File(str);
            files.add(file);
        }

        //在服务器端创建保存临时文件的路径
        String tempFileName = downloadSectionDto.getSectionName() + ".zip";
        String temporaryPath = request.getSession().getServletContext().getRealPath("/WEB-INF/download");
        File file = new File(temporaryPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String tempFilePath = temporaryPath + tempFileName;
        File tempFile = new File(tempFilePath);
        //文件输出流
        FileOutputStream outStream;
        try {

            if (tempFile.createNewFile()) {
                outStream = new FileOutputStream(tempFile);
                ZipOutputStream toClient = new ZipOutputStream(outStream);
                ZipUtil.zipFile(files, toClient);
                toClient.close();
                outStream.close();
                ZipUtil.downloadZip(tempFile, response, downloadSectionDto.getSectionName());
                bean.addSuccess();
            } else {
                logger.info("-------临时文件创建失败--------");
                bean.addDefaultError();
            }
        } catch (Exception e) {
            logger.error("-------系统异常--------", e);
            bean.addError("系统异常");
        }

        return bean;
    }


    /**
     * 全部课件下载
     *
     * @param
     * @return ResponseBean
     * @throws
     * @author suzhao
     * @date 2019年8月12日 下午5:50:51
     */
    @RequestMapping("/allDownLoad")
    @ResponseBody
    public ResponseBean allDownLoadFile(HttpServletRequest request, HttpServletResponse response) {
        ResponseBean bean = new ResponseBean();
        String isTrain = request.getParameter("isTrain");
        String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        String courseId = request.getParameter("courseId");
        //服务器端创建文件临时储存总目录
        String temporaryPath = request.getSession().getServletContext().getRealPath("/WEB-INF/allDownLoad/" + userId + courseId);
        String courseName;
        List<Section> sections = null;

        if (StrUtil.equals("1", isTrain)) {
            TrainCourseVo trainCourse = trainCourseService.selectTrainCourseById(courseId);
            courseName = trainCourse.getTrainCourseName();
            sections = trainSectionService.getSectionByCourseId(courseId);
        } else {
            Course course = courseService.queryCourseByCourseId(courseId);
            courseName = course.getCourseName();
            sections = sectionService.getSectionByCourseId(courseId);
        }

        FileUpDownUtil.allDownLoadUtil(sections, temporaryPath);
        try {
            //压缩总目录
            File zipFile = cn.hutool.core.util.ZipUtil.zip(temporaryPath, temporaryPath + ".zip", true);
            ZipUtil.downloadZip(zipFile, response, courseName);
            //删除临时文件根目录
            File temporaryFile = new File(temporaryPath);
            DeleteFileUtil.deleteDir(temporaryFile);
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("------下载文件异常------", e);
            bean.addError("系统异常");
        }
        return bean;
    }
}
