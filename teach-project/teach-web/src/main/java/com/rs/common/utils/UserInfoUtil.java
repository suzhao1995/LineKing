package com.rs.common.utils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
//		HttpSession session = SessionUtil.getSessionMap().get(sessionKey);
//		String userInfo = String.valueOf(session.getAttribute("userInfo"));
//		if(userInfo != null){
//			String userId = userInfo.split(",")[0];
//			String schoolId = userInfo.split(",")[1];
//			userInfoMap.put("userId", userId);
//			userInfoMap.put("schoolId", schoolId);
//		}
		Map<String,String> userInfo = JedisUtil.getMap("USER_INFO_"+sessionKey);
		if(userInfo != null){
			userInfoMap.put("userId", userInfo.get("userId"));
			userInfoMap.put("schoolId", userInfo.get("schoolId"));
		}
		return userInfoMap;
	}
	
	public static void getRemoteLogin(String sessionKey){
		//获取现有的用户信息
		Map<String,String> userInfos = JedisUtil.getMap("USER_INFO_"+sessionKey);
		
		if(userInfos != null){
			
			Set<String> userSets = JedisUtil.getKeys();
			for(String key : userSets){
				Map<String,String> keyMap = JedisUtil.getMap(key);
				if(keyMap != null && userInfos.get("userId").equals(keyMap.get("userId")) && !key.equals("USER_INFO_"+sessionKey)){
					//删除原有key
					JedisUtil.del(key);
					JedisUtil.set("REMOTE_"+key, "true", 60 * 60 * 24);
					break;
				}
			}
		}
		
	}
	
}