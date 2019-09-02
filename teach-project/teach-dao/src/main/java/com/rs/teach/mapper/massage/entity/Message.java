package com.rs.teach.mapper.massage.entity;

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3597302464457301789L;
	
	private String messageId;	//id
	private String messageType;	//消息类型	1：课程通知  2：平台通知  
	private String messageContent;	//消息内容
	private String messageTime;		//生成消息时间
	private String userId;		//关联的userId
	private String isRead;	//是否已读
	private String isPopUp;	//是否展示到首页弹窗
	private String extend1;	//预留字段
	private String extend2;	//  
	private String extend3;	//
	private String extend4;	//
	
	public Message() {
		
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	public String getIsPopUp() {
		return isPopUp;
	}

	public void setIsPopUp(String isPopUp) {
		this.isPopUp = isPopUp;
	}

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}
	
	
}