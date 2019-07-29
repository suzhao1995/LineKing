package com.rs.common.spring;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rs.common.utils.SessionUtil;

@Component
public class TeachAspect {
	public Object doAround(ProceedingJoinPoint joinPoint){
		Object returnObject = null;
		// 获取session中的用户信息
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    
	    //前后端联调后打开注释 
	    /*if(!isLogin(request)){
	    	//返回登录页面
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("checkTag", "0");
	    	map.put("message", "用户未登录");
	    	returnObject = map;
	    	return returnObject;
	    }*/
	    try {
			returnObject = joinPoint.proceed();
		} catch (Throwable e) {
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
