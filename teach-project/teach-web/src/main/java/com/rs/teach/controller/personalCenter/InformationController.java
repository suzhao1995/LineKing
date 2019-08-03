package com.rs.teach.controller.personalCenter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rs.common.utils.DateUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.message.MessageService;

/**
* InformationController.java
* @Description:个人资料及消息发送controller
* @author: suzhao
* @date: 2019年8月2日 下午1:01:33
* @version: V1.0
*/
@RequestMapping(value="/information")
@Controller
public class InformationController{
	private static final Logger logger = Logger.getLogger(InformationController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	
	/**
	* 个人中心-初始化个人信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月2日 下午1:24:55
	*/
	@RequestMapping("/initUserInfo")
	@ResponseBody
	public ResponseBean initUserInfo(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取登录的用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		User userInfo = userService.getUserById(userId);
		if(userInfo != null){
			bean.addSuccess(userInfo);
		}else{
			bean.addError("系统异常");
			logger.info("--------查询用户信息异常-------");
		}
		return bean;
	}
	/**
	* 个人中心-修改个人信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月2日 下午1:24:55
	*/
	@RequestMapping("/modifyUserInfo")
	@ResponseBody
	public ResponseBean modifyUserInfo(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取登录的用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		//获取用户修改信息
		String userName = request.getParameter("userName");
		String serialNumber = request.getParameter("serialNumber");
		String passWord = request.getParameter("passWord");
		//查询用户原始信息，判断用户是否修改过原始密码
		User userInfo = userService.getUserById(userId);
		if(userInfo != null){
			userInfo.setUserName(userName);
			userInfo.setPassWord(passWord);
			userInfo.setSerialNumber(serialNumber);
			userInfo.setModifier(userId);
			userInfo.setUpdate(DateUtil.dateFormat(new Date(), "yyyy-MM-dd hh:mm:ss"));
			String isDefault = userInfo.getIsDefault();
			if("0".equals(isDefault) && !passWord.equals(userInfo.getPassWord())){
				userInfo.setIsDefault("1");
			}
			int result = userService.modifyUser(userInfo);
			if(result == 0){
				bean.addError("系统异常");
				return bean;
			}
		}
		bean.addSuccess();
		return bean;
	}
	
	/**
	* 初始化消息
	* @param  
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月3日 下午4:56:38
	*/
	@RequestMapping("/initMessage")
	@ResponseBody
	public ResponseBean initMessage(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		return bean;
	}
}