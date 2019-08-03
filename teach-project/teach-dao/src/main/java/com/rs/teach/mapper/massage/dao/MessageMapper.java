package com.rs.teach.mapper.massage.dao;

import com.rs.teach.mapper.massage.entity.Message;

/**
* MessageMapper.java
* @Description:消息接口
* @author: suzhao
* @date: 2019年8月3日 下午4:28:05
* @version: V1.0
*/
public interface MessageMapper{
	/**
	* 插入消息内容
	* @param message
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月3日 下午4:28:17
	*/
	public int insertMassage(Message message);
	
	  
}