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
		// ��ȡsession�е��û���Ϣ
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    
	    //ǰ����������ע�� 
	    /*if(!isLogin(request)){
	    	//���ص�¼ҳ��
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("checkTag", "0");
	    	map.put("message", "�û�δ��¼");
	    	returnObject = map;
	    	return returnObject;
	    }*/
	    try {
			returnObject = joinPoint.proceed();
		} catch (Throwable e) {
			System.out.println("=========�����쳣========"+e);
		}
		return returnObject;
	}
	
	
	public boolean isLogin(HttpServletRequest request){
		boolean isLogin = false;
		//�ж��û��Ƿ��¼
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
