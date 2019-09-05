package com.rs.teach.service.message.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.massage.dao.MessageMapper;
import com.rs.teach.mapper.massage.entity.Message;
import com.rs.teach.service.message.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	
	private Logger logger = Logger.getLogger(MessageServiceImpl.class);
	 
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

	@Override
	public List<Message> getMessages() {
		return mapper.queryMessages();
	}

	@Override
	public int addMessages(Message message) {
		return mapper.addMessages(message);
	}

	@Override
	public List<Message> getMessage(String userId) {
		List<Message> list = mapper.queryMessageByPop(userId);
		//修改isPopUp的值
		try {
			mapper.updateIsPop(list);
		} catch (Exception e) {
			logger.error("---修改isPopUp值失败---", e);
		}
		return list;
	}
	
}