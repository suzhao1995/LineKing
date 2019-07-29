package com.rs.teach.mapper.user.dao;

import com.rs.teach.mapper.user.entity.User;

/**
* UserMapper.java
* @Description:用户dao层接口
* @author: suzhao
* @date: 2019年7月23日 下午4:43:05
* @version: V1.0
*/
public interface UserMapper {
	/**
	* 根据userId查询用户信息
	* @param id
	* @throws
	* @return user
	* @author suzhao
	* @date 2019年7月24日 下午3:48:33
	*/
	public User getUserById(String id);
	
	/**
	* 查询用户是否修改过个人信息
	* @param id
	* @throws 
	* @return int
	* @author suzhao
	* @date 2019年7月24日 下午3:50:04
	*/
	public int isUpdateInfo(String id);
}
