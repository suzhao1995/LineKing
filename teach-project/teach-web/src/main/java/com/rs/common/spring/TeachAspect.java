package com.rs.common.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.SessionUtil;

@Component
public class TeachAspect {
	public Object doAround(ProceedingJoinPoint joinPoint){
		Object returnObject = null;
		// 获取session中的用户信息
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    Signature signature = joinPoint.getSignature();
	    String methodName = signature.getName();
	    
	    //前后端联调后打开注释  方法名已verify开头的方法不需要进行登录验证
	    if(!isLogin(request) && !methodName.toUpperCase().startsWith("VERIFY")){
	    	//返回登录页面
	    	ResponseBean bean = new ResponseBean();
	    	bean.addError("-1", "用户未登录");
	    	returnObject = bean;
	    	return returnObject;
	    }
	    try {
			returnObject = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("=========出现异常========"+e);
		}
		return returnObject;
	}
	
	
	public boolean isLogin(HttpServletRequest request){
		boolean isLogin = false;
		//判断用户是否登录
	    HttpSession session = null;
	    String sessionId = "";
	    if(StringUtils.isNotBlank(request.getParameter("sessionKey"))){
	    	sessionId = request.getParameter("sessionKey");
	    }else if(StringUtils.isNotBlank(request.getHeader("sessionKey"))){
	    	sessionId = request.getHeader("sessionKey");
	    }
	    if(sessionId != null){
	    	session = (HttpSession) SessionUtil.getSessionMap().get(sessionId);
			if (session != null) {
				String sessionValue = (String) session.getAttribute("userInfo");
				if (StringUtils.isNotBlank(sessionValue)) {
					isLogin = true;
				}
			}
	    }
	    return isLogin;
	}
}
