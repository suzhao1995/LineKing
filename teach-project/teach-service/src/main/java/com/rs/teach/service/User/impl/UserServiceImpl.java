package com.rs.teach.service.User.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.user.dao.UserMapper;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.User.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper dao;
	
	@Override
	public String test() {
		return dao.getUserById("0001").getUserName();
	}
	
	
	/**
	* 根据ID查询用户信息
	* @param id用户名
	* @throws
	* @return user 
	* @author suzhao
	* @date 2019年7月22日 下午5:00:49
	*/
	@Override
	public User getUserById(String id) {
		return dao.getUserById(id);
	}


	@Override
	public boolean isModifyInfo(String id) {
		int count = dao.isUpdateInfo(id);
		if(count == 0){
			return false;
		}
		return true;
	}

	
}
