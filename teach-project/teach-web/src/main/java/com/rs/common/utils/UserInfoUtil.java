package com.rs.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
* UserInfoUtil.java
* @Description:用户信息工具类
* @author: suzhao
* @date: 2019年7月23日 下午5:31:24
* @version: V1.0
*/
public class UserInfoUtil{
	/**
	* 获取userId
	* @param sessionKey
	* @throws
	* @return userId
	* @author suzhao
	* @date 2019年7月23日 下午5:32:41
	*/
	public static Map<String,Object> getUserInfo(String sessionKey){
		Map<String,Object> userInfoMap = new HashMap<String,Object>();
		HttpSession session = SessionUtil.getSessionMap().get(sessionKey);
		String userInfo = String.valueOf(session.getAttribute("userInfo"));
		if(userInfo != null){
			String[] userInfos = userInfo.split(",");
			userInfoMap.put("userId", userInfos[0]);
			userInfoMap.put("userName", userInfos[1]);
		}
		return userInfoMap;
	}
	
}