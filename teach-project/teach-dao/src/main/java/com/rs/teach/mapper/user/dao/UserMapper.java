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
	* 根据userId关联图片资源表查询
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
	
	/**
	* 修改用户信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:04:00
	*/
	public int updateUser(User user);
	
	/**
	* 根据userId 查询用户表
	* @param 
	* @throws
	* @return User
	* @author suzhao
	* @date 2019年8月2日 下午4:26:48
	*/
	public User getTeachUser(String id);
}
