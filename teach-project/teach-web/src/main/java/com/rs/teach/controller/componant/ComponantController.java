package com.rs.teach.controller.componant;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.service.section.SectionService;

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
	
	@RequestMapping("/sectionDownLoad")
	@ResponseBody
	public ResponseBean sectionDownLoad(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取文件
		String sectionId = request.getParameter("sectionId");
		Section section = sectionService.getSectionById(sectionId);
		
		try {
			Map<String,Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, section);
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
	public ResponseBean upLoadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file){
		ResponseBean bean = new ResponseBean();
		//上传文件
		Map<String,Object> resultMap = FileUpDownUtil.fileUpLoad(request, response, file);
		if(resultMap != null && "0".equals(resultMap.get("code"))){
			bean.addSuccess(resultMap);
		}else{
			bean.addError(resultMap.get("message").toString());
		}
		return bean;
	}
	
}