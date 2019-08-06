package com.rs.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.rs.teach.mapper.section.entity.Section;

/**
* FileUpDownUtil.java
* @Description:文件上传下载工具类
* @author: suzhao
* @date: 2019年8月1日 上午11:40:20
* @version: V1.0
*/
public class FileUpDownUtil{
	
	private static final List<String> upLoadType = new ArrayList<String>();	//指定上传文件格式
	private static Logger logger = Logger.getLogger(FileUpDownUtil.class);
	
	static{
		upLoadType.add(".txt");
		upLoadType.add(".jpg");
		upLoadType.add(".png");
		upLoadType.add(".doc");
		upLoadType.add(".docx");
		upLoadType.add(".ppt");
		upLoadType.add(".pdf");
	}
	
	/**
	* 图片上传 表单指定enctype="multipart/form-data"
	* @param 
	* @throws
	* @return Map<String,Object>
	* @author suzhao
	* @date 2019年8月1日 下午4:17:25
	*/
	public static Map<String,Object> picUpLoad(HttpServletRequest request, HttpServletResponse response, MultipartFile file){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//文件保存路径
		String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload/img");	//保存的文件根目录
		
		if(!file.isEmpty()){
			try {
				String upLoadId = UUID.randomUUID().toString().replace("-", "");//生成章节id
				
				String updateFileName = file.getOriginalFilename().split("[.]")[0];
				String sectionType = "."+file.getOriginalFilename().split("[.]")[1];
				if(!upLoadType.contains(sectionType)){
					resultMap.put("code", "-1");
					resultMap.put("message", "上传文件格式错误！");
					return resultMap;
				}
				//使用hash算法散列存储文件位置
				Map<String,String> dirPathMap = findFileSavePathByFileName(updateFileName,savePath);
				String dirPath = dirPathMap.get("dir");
				String saveRealName = upLoadId+sectionType;
				
				file.transferTo(new File(dirPath + "/" + saveRealName));
				
				resultMap.put("picUrl", dirPath + "/" + saveRealName);	
				resultMap.put("picId", upLoadId);	//生成的随机章节ID，唯一
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
			}
			
		}else{
			resultMap.put("code", "-1");
			resultMap.put("message", "上传文件为空");
		}
		return resultMap;
	}
	
	/**
	* 课件上传方法 表单指定enctype="multipart/form-data"
	* @param 
	* @throws
	* @return Map<String,Object>
	* @author suzhao
	* @date 2019年8月1日 下午4:17:25
	*/
	public static Map<String,Object> fileUpLoad(HttpServletRequest request, HttpServletResponse response, MultipartFile file){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//String courseId = request.getParameter("courseId");	//课程资源ID
		//文件保存路径
		String savePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");	//保存的文件根目录
		
		if(!file.isEmpty()){
			try {
				String upLoadId = UUID.randomUUID().toString().replace("-", "");//生成章节id
				
				String updateFileName = file.getOriginalFilename().split("[.]")[0];
				String sectionType = "."+file.getOriginalFilename().split("[.]")[1];
				if(!upLoadType.contains(sectionType)){
					resultMap.put("code", "-1");
					resultMap.put("message", "上传文件格式错误！");
					return resultMap;
				}
				//使用hash算法散列存储文件位置
				Map<String,String> dirPathMap = findFileSavePathByFileName(updateFileName,savePath);
				String dirPath = dirPathMap.get("dir");
				String saveRealName = upLoadId+"_"+file.getOriginalFilename();
				
				file.transferTo(new File(dirPath + "/" + saveRealName));
				
				String officeUrl = dirPath + "/" + saveRealName;
				String pdfUrl = dirPath + "/" + upLoadId+"_"+updateFileName+".pdf";
				//调用pdf转换类
				if(!".pdf".equals(sectionType)){
					
					Map<String,Object> officeMap = Office2PdfUtil.Word2Pdf(officeUrl, pdfUrl);
					if("-1".equals(officeMap.get("resultCode"))){
						resultMap.put("code", "-1");
						resultMap.put("message", "文件上传异常");
						//删除原始文件
						File officeFile = new File(dirPath + "/" + saveRealName);
						if(officeFile.exists()){
							//删除
							officeFile.delete();
						}
						logger.error("---------文件上传异常---------");
						return resultMap;
					}
				}
				resultMap.put("upLoadId", upLoadId);	//生成的随机章节ID，唯一	上传为章节课件 upLoadId = sectionId， 上传为修改课件， upLoadId = upLoadId
				resultMap.put("updateFileName", updateFileName);
				resultMap.put("sectionUrl", dirPathMap.get("sortDir"));
				resultMap.put("sectionType", sectionType);
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
			}
			
		}else{
			resultMap.put("code", "-1");
			resultMap.put("message", "上传文件为空");
		}
		return resultMap;
	}
	/**
	* 文件下载方法
	* @param sectionId 文件的唯一id，上传文档的uploadId
	* @param upLoadName 文件名，保存在服务器的文件名 
	* @param sectionUrl 保存文件的路径 根目录为/WEB-INF/upload，取上传方法 sectionUrl
	* @param sectionType 保存文件的文档类型
	* @param downLoadName 下载的文件名称
	* @throws
	* @return void
	* @author suzhao
	* @throws IOException 
	* @date 2019年8月1日 上午11:46:24
	*/
	public static Map<String,Object> fileDownLoad(HttpServletRequest request, HttpServletResponse response, String sectionId, String upLoadName, String sectionUrl, String sectionType, String downLoadName) throws IOException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//上传文件时，设置文件的随机id为文件名
		
		String fileName = sectionId +"_"+ upLoadName;	//保存在服务器文件的名称
		//保存文件的地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
		filePath = filePath + sectionUrl.replace("/", "\\");
		
		String fileRealPath = filePath + "\\" + fileName + sectionType; 
		File file = new File(fileRealPath);
		if(!file.exists()){
			resultMap.put("code", "-1");
			resultMap.put("message", "您想要下载的资源不存在或已被删除！");
			return resultMap;
		}
		//设置响应头，控制浏览器下载该文件
		response.setContentType("multipart/form-data");
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downLoadName, "UTF-8"));
		//读取文件，保存文件到输入流
		FileInputStream inputStream = new FileInputStream(file);
		//创建输出流
		OutputStream out = response.getOutputStream();
		//创建缓冲区
		byte[] buffer = new byte[1024];
		int len = 0;
		//将文件读入缓冲区
		while((len = inputStream.read(buffer)) > 0){
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
     * @Method: findFileSavePathByFileName
     * @Description: 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
     * @Anthor:suzhao
     * @param filename 要下载的文件名
     * @param saveRootPath 上传文件保存的根目录
     * @return 要下载的文件的存储目录
     */ 
    public static Map<String,String> findFileSavePathByFileName(String filename, String saveRootPath){
    	Map<String,String> pathMap = new HashMap<String,String>();
    	
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15
        String dir = saveRootPath + "/" + dir1 + "/" + dir2;
        String sortDir = "/"+ dir1 + "/" + dir2;
        pathMap.put("dir", dir);
        pathMap.put("sortDir", sortDir);
        
        File file = new File(dir);
        if(!file.exists()){
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