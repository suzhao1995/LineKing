package com.rs.teach.controller.componant;

import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.resourcesAttr.PicAttrService;
import com.rs.teach.service.section.SectionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
* ComponantController.java
* @Description:公共服务组件Controller
* @author: suzhao
* @date: 2019年8月1日 下午1:35:22
* @version: V1.0
*/
@Controller
@RequestMapping(value = "/componant")
public class ComponantController{
	private Logger logger  = Logger.getLogger(ComponantController.class);
	
	/**
	 * 章节service
	 * */
	@Autowired
	private SectionService sectionService;
	
	/**
	 * 图片属性service
	 * */
	@Autowired
	private PicAttrService picAttrService;
	
	@Autowired
	private UserService userService;
	
	@Value("${filePath}")
	private String filePath;	//文件存放根目录
	
	/**
	* 课件资源下载
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月2日 下午12:46:35
	*/
	@RequestMapping("/sectionDownLoad")
	@ResponseBody
	public ResponseBean sectionDownLoad(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取文件
		String sectionId = request.getParameter("sectionId");
		Section section = sectionService.getSectionById(sectionId);
		try {
			String fileRealPath = filePath + section.getSectionUrl().replace("/", "\\")+"\\" + section.getCoursewareId() +"_"+ section.getUpdateFileName() + section.getSectionType();
			Map<String,Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, fileRealPath, section.getSectionType(), section.getSectionName());
			if(resultMap != null && "0".equals(resultMap.get("code"))){
				bean.addSuccess();
			}else{
				bean.addError(resultMap.get("message").toString());
			}
		} catch (Exception e) {
			logger.error("--------文件下载异常-------", e);
			bean.addError("系统异常");
		}
		return bean;
	}
	
	/**
	* 课件上传
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月1日 下午5:27:45
	*/
	@RequestMapping("/upLoadFile")
	@ResponseBody
	public ResponseBean upLoadFile(HttpServletRequest request,  @RequestParam("file") MultipartFile file){
		ResponseBean bean = new ResponseBean();
		//上传文件
		Map<String,Object> resultMap = FileUpDownUtil.fileUpLoad(request, file);
		if(resultMap != null && "0".equals(resultMap.get("code"))){
			bean.addSuccess(resultMap);
		}else{
			bean.addError(resultMap.get("message").toString());
		}
		return bean;
	}
	
	/**
	* 用户图像上传
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月1日 下午5:27:45
	*/
	@RequestMapping("/upLoadPic")
	@ResponseBody
	public ResponseBean upLoadPic(HttpServletRequest request, @RequestParam("file") MultipartFile file){
		ResponseBean bean = new ResponseBean();
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();

		//上传文件
		Map<String,Object> resultMap = FileUpDownUtil.picUpLoad(request, file);

		if(resultMap != null && "0".equals(resultMap.get("code"))){
			//判断用户是否上传过图片
			User userInfo = userService.getUserById(userId);
			PicAttr pic = userInfo.getAttr();
			int result = 0;
			if(pic == null){
				//新增
				PicAttr picAttr = new PicAttr();
				picAttr.setAssociationId(userId);
				picAttr.setPicId(resultMap.get("picId").toString());
				picAttr.setPicUrl(resultMap.get("picUrl").toString());
				result = picAttrService.addPic(picAttr);
			}else{
				pic.setPicUrl(resultMap.get("picUrl").toString());
				result = picAttrService.modifyPic(pic);
			}
			if(result == 0){
				bean.addDefaultError();
				return bean;
			}
			bean.addSuccess(resultMap);
		}else{
			bean.addError(resultMap.get("message").toString());
		}
		return bean;
	}

}