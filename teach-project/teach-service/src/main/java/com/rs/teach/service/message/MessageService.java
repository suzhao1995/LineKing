package com.rs.teach.service.message;

import java.util.List;


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
	
	/**
	* 根据userId查询用户所有消息，按时间降序排列
	* @param 
	* @throws
	* @return List<Message>
	* @author suzhao
	* @date 2019年8月5日 上午11:27:26
	*/
	public List<Message> getMessageById(String userId, String code);
	
	/**
	* 查询未读的消息
	* @param 
	* @throws
	* @return List<Message>
	* @author suzhao
	* @date 2019年8月5日 上午11:29:06
	*/
	public List<Message> queryNotRead(String userId);
	
	/**
	* 修改用户消息IsRead的状态
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月5日 上午11:36:19
	*/
	public int modifyIsRead(String userId,String messageId);
	
	//管理员 start
	/**
	* 查询所有消息
	* @param 
	* @throws
	* @return List<Message>
	* @author suzhao
	* @date 2019年9月2日 下午6:21:50
	*/
	public List<Message> getMessages();
	
	/**
	* 批量新增
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年9月2日 下午6:44:44
	*/
	public int addMessages(Message message);
}