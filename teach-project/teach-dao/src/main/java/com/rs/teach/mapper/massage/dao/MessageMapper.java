package com.rs.teach.mapper.massage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	* 根据userId查询用户所有消息，按时间降序排列
	* @param 
	* @throws
	* @return List<Message>
	* @author suzhao
	* @date 2019年8月5日 上午10:24:47
	*/
	public List<Message> queryMessage(@Param("userId") String userId, @Param("code") String code);
	
	/**
	* 获取未读的消息
	* @param 
	* @throws
	* @return List<Message>
	* @author suzhao
	* @date 2019年8月5日 上午11:25:41
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
	public int updateIsRead(@Param("userId") String userId, @Param("messageId") String messageId);
	
	/**
	* 查询首页弹窗的所有消息
	* @param 
	* @throws
	* @return List<Message>
	* @author suzhao
	* @date 2019年9月5日 下午6:09:19
	*/
	public List<Message> queryMessageByPop(String userId);
	
	/**
	* 批量修改isPopUp的值
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月5日 下午6:29:59
	*/
	public void updateIsPop(List<Message> messages);
	
	//管理员 start
	
	/**
	* 查询所有消息
	* @param 
	* @throws
	* @return List<Message>
	* @author suzhao
	* @date 2019年9月2日 下午6:22:23
	*/
	public List<Message> queryMessages(String userId);
	
	/**
	* 批量新增
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年9月2日 下午6:45:37
	*/
	public int addMessages(Message message);
}