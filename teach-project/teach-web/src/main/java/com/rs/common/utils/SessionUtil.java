package com.rs.common.utils;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
 

public class SessionUtil {
	private static final Long sessionMaxAge = 86400L;
 
	private SessionUtil() {
 
	}
 
	private static class SessionUtilInner {
		private static final SessionUtil SESSION_UTIL = new SessionUtil();
	}
 
	public static SessionUtil getSessionUtil() {
		return SessionUtilInner.SESSION_UTIL;
	}
 
	private static ConcurrentHashMap<String, HttpSession> sessionMap;
	//异地登录存储map
	private static ConcurrentHashMap<String, String> remoteLoginMap;
	static {
		sessionMap = new ConcurrentHashMap<>();
		remoteLoginMap =  new ConcurrentHashMap<String, String>();
	}
 
	public static ConcurrentHashMap<String, HttpSession> getSessionMap() {
		sessionMap = cleanMap(sessionMap);
		return sessionMap;
	}
	
	public static  ConcurrentHashMap<String, String> getRemoteLoginMap(){
		remoteLoginMap = cleanRemoteMap(remoteLoginMap);
		return remoteLoginMap;
	}
	
	//清除过期的session信息
	private static synchronized ConcurrentHashMap<String, HttpSession> cleanMap(
			ConcurrentHashMap<String, HttpSession> map) {
		System.out.println("----------map.size()----------"+map.size());
		if (map.size() < 1) {
			return map;
		}
 
		Set<Entry<String, HttpSession>> entrySet = map.entrySet();
		List<String> list = new ArrayList<>();
		for (Entry<String, HttpSession> entry : entrySet) {
			System.out.println("---------session---------"+entry.getValue());
			// 如果session过期了，就清除掉这个session
			long max_age = sessionMaxAge * 1000L;
			long time = new Date().getTime();
			long creationTime = 0L;
			try {
				creationTime = entry.getValue().getCreationTime();
			} catch (Exception e) {
				list.add(entry.getKey());
				continue;
			}
			long sessionAge = time - creationTime;
			if (sessionAge > max_age) {
				list.add(entry.getKey());
				entry.getValue().setMaxInactiveInterval(1);
			}
		}
 
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				map.remove(list.get(i));
			}
		}
		list = null;
		return map;
	}
	
	//重复登录，清除上一次用户登录的信息
	public static synchronized void cleanOldSession(String userInfo) {
		if (sessionMap != null && sessionMap.size() > 0) {
			Set<Entry<String, HttpSession>> entrySet = sessionMap.entrySet();
			String sessionKey = "";
			for (Entry<String, HttpSession> entry : entrySet) {
				if (StringUtils.isNotBlank(userInfo)
						&& userInfo.equals((String) entry.getValue().getAttribute("userInfo"))) {
					sessionKey = entry.getKey();
					entry.getValue().setMaxInactiveInterval(1);
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			if (StringUtils.isNotBlank(sessionKey)) {
				sessionMap.remove(sessionKey);
				remoteLoginMap.put(sessionKey, String.valueOf(new Date().getTime()));	//添加进异地登录Map,并在切面进行判断
			}
		}
	}
	
	//清除异地登录的过期信息 
	private static synchronized  ConcurrentHashMap<String,String> cleanRemoteMap(ConcurrentHashMap<String,String> map){
		if(map.size() < 1){
			return map;
		}
		List<String> list = new ArrayList<>();	//保存清除数据的list集合
		
		Set<Entry<String, String>> entrySet = map.entrySet();
		for(Entry<String, String> entry : entrySet){
			//获取当前时间	精确到秒
			long nowTime = new Date().getTime();
			long remoteTime = Long.valueOf(entry.getValue());
			long maxAge = sessionMaxAge * 1000L;
			long remoteAge = nowTime - remoteTime;
			if(remoteAge > maxAge){
				list.add(entry.getKey());
			}
			
		}
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				map.remove(list.get(i));
			}
		}
		list = null;
		return map;
	}
	public static synchronized void cleanOldRemoteMap(String sessionId){
		if(remoteLoginMap != null && remoteLoginMap.size() > 0){
			remoteLoginMap.remove(sessionId);
		}
	}
	
}