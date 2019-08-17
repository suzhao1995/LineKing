package com.rs.teach.controller.materiel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.materiel.entity.Materiel;
import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.materiel.MaterielService;

/**
* MaterielController.java
* @Description:物料下载Controller
* @author: suzhao
* @date: 2019年8月17日 上午11:37:47
* @version: V1.0
*/
@RequestMapping(value = "/materiel")
@Controller
public class MaterielController {
	private static final Logger logger = Logger.getLogger(MaterielController.class);
	/**
	 * 物料服务
	 */
	@Autowired
	private MaterielService materielService;
	
	/**
	* 分页查询物料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/initMateriel")
	@ResponseBody
	public ResponseBean initMateriel(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		
		//初始化课程信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//分页查询物料信息
		List<Materiel> list = materielService.getMateriel("1");
		PageInfo<Materiel> info = new PageInfo<Materiel>(list,9);
		bean.addSuccess(info);
		return bean;
	}
	
	/**
	* 下载物料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午2:02:56
	*/
	@RequestMapping("/downLoadMateriel")
	@ResponseBody
	public ResponseBean downLoadMateriel(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String materielId = request.getParameter("materielId");
		
		Materiel materiel = materielService.getMaterielById(materielId);
		//
		try {
			String[] types = materiel.getMaterielUrl().split("\\\\");
			String type = "."+types[types.length-1].split("[.]")[1];
			Map<String,Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, materiel.getMaterielUrl(), type, materiel.getMaterielName());
			if(resultMap != null && "0".equals(resultMap.get("code"))){
				bean.addSuccess();
			}else{
				bean.addError(resultMap.get("message").toString());
			}
		} catch (IOException e) {
			logger.error("--------文件下载异常-------", e);
			bean.addError("系统异常");
		}
		return bean;
	}
	
	/**
	* 物料查看
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午2:02:56
	*/
	@RequestMapping("/materielDetail")
	@ResponseBody
	public ResponseBean materielDetail(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String materielId = request.getParameter("materielId");
		
		Materiel materiel = materielService.getMaterielById(materielId);
		//
		bean.addSuccess(materiel);
		return bean;
	}
	
	/**
	* 管理员--上传物料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:10:15
	*/
	@RequestMapping("/upLoadMateriel")
	@ResponseBody
	public ResponseBean upLoadMateriel(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile file){
		ResponseBean bean = new ResponseBean();
		String materielName = request.getParameter("materielName");
		String materielDetail = request.getParameter("materielDetail");
		Map<String,Object> resultMap = FileUpDownUtil.materielUpLoad(request, file);
		
		if(resultMap != null && "0".equals(resultMap.get("code"))){
			String materielId = resultMap.get("materielId").toString();
			String materielUrl = resultMap.get("materielUrl").toString();
			
			Materiel materiel = new Materiel();
			materiel.setMaterielId(materielId);
			materiel.setMaterielName(materielName);
			materiel.setMaterielUrl(materielUrl);
			materiel.setMaterielStatus("0");
			materiel.setMaterielDetail(materielDetail);
			
			int result = materielService.addMateriel(materiel);
			if(result == 0){
				bean.addError("上传物料失败");
			}
		}else{
			bean.addError(resultMap.get("message").toString());
		}
		bean.addSuccess();
		return bean;
	}
	
	/**
	* 管理员--上下架操作
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:35:17
	*/
	@RequestMapping("/updateStatus")
	@ResponseBody
	public ResponseBean updateStatus(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String status = request.getParameter("status");
		String materielId = request.getParameter("materielId");
		int result = materielService.modifyStatus(materielId, status);
		if(result == 0){
			bean.addError("上下架失败");
		}
		bean.addSuccess();
		return bean;
	}
	
	
	/**
	* 管理员--初始化物料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:35:17
	*/
	@RequestMapping("/adminInit")
	@ResponseBody
	public ResponseBean adminInit(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		
		//初始化课程信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//分页查询物料信息
		List<Materiel> list = materielService.getMateriel("0");
		PageInfo<Materiel> info = new PageInfo<Materiel>(list,9);
		bean.addSuccess(info);
		return bean;
	}
	
	
}