package com.rs.common.spring;

import cn.hutool.core.util.StrUtil;

import com.rs.common.utils.JedisUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.SessionUtil;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.user.dao.UserMapper;
import com.rs.teach.mapper.user.entity.User;
import com.sun.media.jfxmedia.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class TeachAspect {

	@Autowired
	private UserMapper userMapper;

    /**
     * 判断用户是否登录
     * @param joinPoint
     * @return
     */
	public Object doAround(ProceedingJoinPoint joinPoint){
		Object returnObject = null;
		// 获取session中的用户信息
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	    Signature signature = joinPoint.getSignature();
	    String methodName = signature.getName();

		//前后端联调后打开注释  方法名已verify开头的方法不需要进行登录验证
		if(!methodName.toUpperCase().startsWith("VERIFY")){
			Map<String,Object> resultMap = isLogin(request);
			if("1030".equals(resultMap.get("isLogin"))){
				//异地登录
				ResponseBean bean = new ResponseBean();
				bean.addError("1030", "该账号已在其他地方登录，您已被迫下线，如非本人操作请重新登录并及时修改密码");
				returnObject = bean;
				return returnObject; 
			}
			if("1040".equals(resultMap.get("isLogin"))){
				//返回登录页面
				ResponseBean bean = new ResponseBean();
				bean.addError("-1", "用户未登录");
				returnObject = bean;
				return returnObject; 
			}
		}
	    try {
			returnObject = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("切面-判断用户是否登录-异常");
		}
		return returnObject;
	}

	/**
	 * 判断用户是否是管理员
	 * @param joinPoint
	 * @return
	 */
	public Object checkAround(ProceedingJoinPoint joinPoint){
        Object returnObject = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
	    if(!methodName.toUpperCase().startsWith("VERIFY")){
            String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
            User user = userMapper.getTeachUser(userId);
            if (!StrUtil.equals("1", user.getAdminFlag()) && !StrUtil.equals("2", user.getAdminFlag())) {
                ResponseBean bean = new ResponseBean();
                bean.addError("-1", "用户沒有权限");
                return bean;
            }
        }
        try {
            returnObject = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
			log.error("切面-判断用户是否是管理员-异常");
        }
        return returnObject;
	}

	/**
	 * 判断用户是否是超级管理员
	 * @param joinPoint
	 * @return
	 */
	public Object checkSupperAround(ProceedingJoinPoint joinPoint){
        Object returnObject = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
	    if(!methodName.toUpperCase().startsWith("VERIFY")){
            String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
            User user = userMapper.getTeachUser(userId);
            if (!StrUtil.equals("2", user.getAdminFlag())) {
                ResponseBean bean = new ResponseBean();
                bean.addError("-1", "用户沒有权限");
                log.error("用户不是超级管理员");
                return bean;
            }
        }
        try {
            returnObject = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
			log.error("切面-判断用户是否是超级管理员-异常");
        }
        return returnObject;
	}


	public Map<String,Object> isLogin(HttpServletRequest request){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("isLogin", "1040");	//用户未登录
		//判断用户是否登录
	    HttpSession session = null;
	    String sessionId = "";
	    if(StringUtils.isNotBlank(request.getParameter("sessionKey"))){
			sessionId = request.getParameter("sessionKey");
		}else if(StringUtils.isNotBlank(request.getHeader("sessionKey"))){
			sessionId = request.getHeader("sessionKey");
		}
	    log.info("-----sessionId----"+sessionId);
	    
	    UserInfoUtil.getRemoteLogin(sessionId);
	    String remoteKey = "REMOTE_USER_INFO_"+sessionId;
	    if("true".equals(JedisUtil.get(remoteKey))){
	    	resultMap.put("isLogin", "1030");		//异地登录code
	    	JedisUtil.del(remoteKey);
	    }else{
	    	
	    	if(sessionId != null){
	    		Map<String,String> userInfo = JedisUtil.getMap("USER_INFO_"+sessionId);
	    		if(userInfo != null){
	    			if("true".equals(userInfo.get("isValidate"))){
	    				resultMap.put("isLogin", "0");	//已登录
	    			}
	    		}
//	    		session = (HttpSession) SessionUtil.getSessionMap().get(sessionId);
//	    		if (session != null) {
//	    			String sessionValue = (String) session.getAttribute("userInfo");
//	    			if (StringUtils.isNotBlank(sessionValue)) {
//	    				resultMap.put("isLogin", "0");	//已登录
//	    			}
//	    		}
	    	}
	    }
	    return resultMap;
	}
}
