package com.rs.teach.service.message;

import com.rs.teach.mapper.massage.entity.Message;

public interface MessageService{
	/**
	* 添加消息
	* @param message
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月3日 下午4:39:00
	*/
	public int addMessage(Message message);
}