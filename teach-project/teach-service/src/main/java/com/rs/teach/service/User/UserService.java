package com.rs.teach.service.User;

import com.rs.teach.mapper.user.entity.User;

/**
* UserService.java
* @Description:用户service
* @author: suzhao
* @date: 2019年7月22日 下午4:59:28
* @version: V1.0
*/
public interface UserService {
	public String test();
	
	/**
	* 根据ID查询用户信息
	* @param id用户名
	* @throws
	* @return user 
	* @author suzhao
	* @date 2019年7月22日 下午5:00:49
	*/
	public User getUserById(String id);
	
	/**
	* 用户是否修改过个人信息
	* @param id
	* @throws
	* @return boolean
	* @author suzhao
	* @date 2019年7月24日 下午3:46:41
	*/
	public boolean isModifyInfo(String id);
	
	/**
	* 修改用户信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:03:15
	*/
	public int modifyUser(User user);
	
}
