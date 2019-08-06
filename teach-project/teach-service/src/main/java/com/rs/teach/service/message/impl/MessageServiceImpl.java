package com.rs.teach.service.message.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.massage.dao.MessageMapper;
import com.rs.teach.mapper.massage.entity.Message;
import com.rs.teach.service.message.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	 
	@Autowired
	private MessageMapper mapper;
	
	@Override
	public int addMessage(Message message) {
		return mapper.insertMassage(message);
	}

	@Override
	public List<Message> getMessageById(String userId,String code) {
		return mapper.queryMessage(userId,code);
	}

	@Override
	public List<Message> queryNotRead(String userId) {
		return mapper.queryNotRead(userId);
	}

	@Override
	public int modifyIsRead(String userId, String messageId) {
		return mapper.updateIsRead(userId, messageId);
	}
	
}