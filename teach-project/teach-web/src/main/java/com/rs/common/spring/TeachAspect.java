package com.rs.common.spring;

import cn.hutool.core.util.StrUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.SessionUtil;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.user.dao.UserMapper;
import com.rs.teach.mapper.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
			if(!isLogin(request)){
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
			System.out.println("=========出现异常========"+e);
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
            if (!StrUtil.equals("1", user.getAdminFlag())) {
                ResponseBean bean = new ResponseBean();
                bean.addError("-1", "用户沒有权限");
                return bean;
            }
        }
        try {
            returnObject = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("切面-checkAround-异常");
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
