package com.rs.teach.controller.backstage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.DateUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.massage.entity.Message;
import com.rs.teach.service.message.MessageService;

/**
* BeforeInfoController.java
* @Description:消息管理
* @author: suzhao
* @date: 2019年9月2日 下午6:16:50
* @version: V1.0
*/
@Controller
@RequestMapping(value = "/beforeInfo")
public class BeforeInfoController{
	
	private Logger logger = Logger.getLogger(BeforeInfoController.class);
	
	@Autowired
	private MessageService messageService;
	
	/**
	* 初始化消息列表
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月2日 下午6:18:29
	*/
	@RequestMapping("/initMessage")
	@ResponseBody
	public ResponseBean initMessage(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		
		//初始化分页信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		List<Message> list = messageService.getMessages(userId);
		PageInfo<Message> info = new PageInfo<Message>(list,9);
		ajaxData.put("messageList", info);
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 新增消息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年9月2日 下午6:18:29
	*/
	@RequestMapping("/addMessage")
	@ResponseBody
	public ResponseBean addMessage(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String messageContent = request.getParameter("messageContent");	//消息内容
		String ipPopUp = request.getParameter("isPopUp");	//是否展示到首页弹窗
		
		Message message = new Message();
		message.setMessageContent(messageContent);
		message.setMessageTime(DateUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
		message.setMessageType("2");
		message.setIsPopUp(ipPopUp);
		message.setSendUser(userId);
		int count = messageService.addMessages(message);
		if(count > 0){
			bean.addSuccess();
		}else{
			bean.addDefaultError();
		}
		return bean;
	}
}