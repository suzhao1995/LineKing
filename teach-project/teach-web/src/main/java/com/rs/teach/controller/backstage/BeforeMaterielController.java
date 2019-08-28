package com.rs.teach.controller.backstage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.DateUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.materiel.entity.Materiel;
import com.rs.teach.mapper.sysCode.entity.SysCode;
import com.rs.teach.service.materiel.MaterielService;
import com.rs.teach.service.sysCode.SysCodeService;

/**
* BeforeMaterielController.java
* @Description:物料管理接口
* @author: suzhao
* @date: 2019年8月28日 上午10:40:23
* @version: V1.0
*/
@Controller
@RequestMapping(value = "/beforeMateriel")
public class BeforeMaterielController{
	private static final Logger logger = Logger.getLogger(BeforeMaterielController.class);
	/**
	 * 物料服务
	 */
	@Autowired
	private MaterielService materielService;
	
	@Autowired
	private SysCodeService sysCodeService;
	
	
	/**
	* 管理员--上传物料资料
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
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		Map<String,Object> resultMap = FileUpDownUtil.materielUpLoad(request, file);
		
		if(resultMap != null && "0".equals(resultMap.get("code"))){
			ajaxData.put("materielUrl", resultMap.get("materielUrl"));
			ajaxData.put("materielId", resultMap.get("materielId"));
		}else{
			bean.addError(resultMap.get("message").toString());
			logger.info("--------上传物料封面失败--------"+resultMap.get("message"));
		}
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 管理员--上传物料封面
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:10:15
	*/
	@RequestMapping("/upLoadMaterielImg")
	@ResponseBody
	public ResponseBean upLoadMaterielImg(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile file){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		Map<String,Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
		
		if(resultMap != null && "0".equals(resultMap.get("code"))){
			ajaxData.put("materielPath", resultMap.get("picUrl"));
		}else{
			bean.addError(resultMap.get("message").toString());
			logger.info("--------上传物料封面失败--------"+resultMap.get("message"));
		}
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 管理员--保存上传的物料信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:10:15
	*/
	@RequestMapping("/saveMateriel")
	@ResponseBody
	public ResponseBean saveMateriel(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String materielName = request.getParameter("materielName");
		String materielDetail = request.getParameter("materielDetail");
		String materielId = request.getParameter("materielId");
		String materielUrl = request.getParameter("materielUrl");
		String materielPath = request.getParameter("materielPath");
		String materielCode = request.getParameter("code");
		String createDate = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		
		Materiel materiel = new Materiel();
		materiel.setMaterielId(materielId);
		materiel.setMaterielName(materielName);
		materiel.setMaterielUrl(materielUrl);
		materiel.setMaterielStatus("1");
		materiel.setMaterielDetail(materielDetail);
		materiel.setMaterielPath(materielPath);
		materiel.setMaterielType(materielCode);
		materiel.setCreateDate(createDate);
		
		int result = materielService.addMateriel(materiel);
		if(result == 0){
			bean.addError("上传物料失败");
			return bean;
		}
		
		bean.addSuccess();
		return bean;
	}
	
	/**
	* 管理员--删除物料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:35:17
	*/
	@RequestMapping("/delMateriel")
	@ResponseBody
	public ResponseBean delMateriel(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String materielId = request.getParameter("materielId");
		if(StringUtils.isEmpty(materielId)){
			bean.addError("请选择一条记录");
			return bean;
		}
		try {
			materielService.delMateriel(materielId);
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("删除物料失败");
			logger.info("------删除物料失败------"+e);
		}
		return bean;
	}
	
	/**
	* 管理员--编辑物料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:35:17
	*/
	@RequestMapping("/updateMateriel")
	@ResponseBody
	public ResponseBean updateMateriel(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String materielId = request.getParameter("materielId");
		if(StringUtils.isEmpty(materielId)){
			bean.addError("请选择一条记录");
			return bean;
		}
		String materielName = request.getParameter("materielName");
		String materielDetail = request.getParameter("materielDetail");
		String materielUrl = request.getParameter("materielUrl");
		String materielPath = request.getParameter("materielPath");
		String materielCode = request.getParameter("code");
		String createDate = DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
		
		Materiel materiel = new Materiel();
		materiel.setMaterielId(materielId);
		materiel.setMaterielName(materielName);
		materiel.setMaterielUrl(materielUrl);
		materiel.setMaterielStatus("1");
		materiel.setMaterielDetail(materielDetail);
		materiel.setMaterielPath(materielPath);
		materiel.setMaterielType(materielCode);
		materiel.setCreateDate(createDate);
		try {
			materielService.modifyMateriel(materiel);
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("修改物料失败");
			logger.info("------修改物料失败------"+e);
		}
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
		
		String code = request.getParameter("code");
		if("all".equals(code)){
			code = null;
		}
		//初始化课程信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//分页查询物料信息
		List<Materiel> list = materielService.adminGetMateriel(code);
		PageInfo<Materiel> info = new PageInfo<Materiel>(list,9);
		bean.addSuccess(info);
		return bean;
	}
	/**
	* 管理员--搜索物料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午4:35:17
	*/
	@RequestMapping("/searchMateriel")
	@ResponseBody
	public ResponseBean searchMateriel(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		String searchName = request.getParameter("searchName");
		//初始化课程信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//分页查询物料信息
		List<Materiel> list = materielService.getMaterielByName(searchName);
		PageInfo<Materiel> info = new PageInfo<Materiel>(list,9);
		bean.addSuccess(info);
		return bean;
	}
	
	/**
	* 管理员---查询物料分类
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/initMaterielType")
	@ResponseBody
	public ResponseBean initMaterielType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		//初始化物料分类
		List<SysCode> list = sysCodeService.getSysCodeList("MATERIEL_CODE");
		ajaxData.put("materielType", list);
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 管理员---删除物料分类
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/delMaterielType")
	@ResponseBody
	public ResponseBean delMaterielType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String cid = request.getParameter("cid");	//字典主键
		
		if(StringUtils.isEmpty(cid)){
			bean.addError("请选择一条记录");
			return bean;
		}
		//判断该物料分类是否有物料
		SysCode sysCode = sysCodeService.getSysCode(cid);
		if(sysCode != null){
			List<Materiel> list = materielService.getMateriel("1",sysCode.getCode());
			if(list.size() > 0){
				bean.addError("该物料分类中含有物料资源，请先删除物料资源");
				return bean;
			}
		}
		//删除物料分类
		sysCodeService.delSysCode(cid);
		bean.addSuccess();
		return bean;
	}
	
	/**
	* 管理员---新增物料分类
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/addMaterielType")
	@ResponseBody
	public ResponseBean addMaterielType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String typeName = request.getParameter("typeName");	//分类名称
		//查询物料分类总数 限定6个
		List<SysCode> list = sysCodeService.getSysCodeList("MATERIEL_CODE");
		if(list.size() >= 6){
			bean.addError("分类已达上限");
			return bean;
		}
		SysCode sysCode = new SysCode();
		sysCode.setCid(DateUtil.dateFormat(new Date(), "yyyyMMddHHmmss"));
		sysCode.setCodeValue(typeName);
		sysCode.setCodeType("MATERIEL_CODE");
		sysCode.setCreateBy(userId);
		sysCode.setCreateDate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		if(list.size() > 0){
			SysCode lastCode = list.get(list.size() - 1);	//获取list集合最后一个对象
			int code = Integer.valueOf(lastCode.getCode()) + 1;
			int sort = Integer.valueOf(lastCode.getCodeSort()) + 10;
			sysCode.setCode(String.valueOf(code));
			sysCode.setCodeSort(String.valueOf(sort));
		}else{
			sysCode.setCode("1001");
			sysCode.setCodeSort("1010");
		}
		try {
			int result = sysCodeService.addSysCode(sysCode);
			if(result == 0){
				bean.addError("新增物料分类失败");
				return bean;
			}
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("系统异常");
			logger.info("---------新增物料分类失败，系统异常--------", e);
		}
		
		return bean;
	}
	
	
	/**
	* 管理员---修改物料分类
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月17日 下午1:14:05
	*/
	@RequestMapping("/updateMaterielType")
	@ResponseBody
	public ResponseBean updateMaterielType(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String typeName = request.getParameter("typeName");	//分类名称
		String cid = request.getParameter("cid");	
		
		SysCode sysCode = new SysCode();
		sysCode.setCid(cid);
		sysCode.setCodeValue(typeName);
		sysCode.setCodeType("MATERIEL_CODE");
		sysCode.setCreateBy(userId);
		sysCode.setCreateDate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		
		try {
			int result = sysCodeService.modifySysCode(sysCode);
			if(result == 0){
				bean.addError("修改物料分类失败");
				return bean;
			}
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("系统异常");
			logger.info("---------修改物料分类失败，系统异常--------", e);
		}
		
		return bean;
	}
	
	
}