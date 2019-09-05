package com.rs.teach.mapper.user.entity;

import java.io.Serializable;

import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;

/**
* User.java
* @Description:用户实体
* @author: suzhao
* @date: 2019年7月22日 下午5:06:47
* @version: V1.0
*/
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -54518571697390741L;
	
	private String userId; 		//用户名
	private String userName;	//用户名称
	private String passWord;	//用户登录密码
	private String schoolId;	//校区id
	private String endDate;		//账号到期时间
	private String adminFlag;	//权限（0为用户，1为管理员，2为超级管理员）
	private String isDefault;	//是否为默认：0：默认；1：已修改
	private String startTime; 	//创建时间
	private String update;		//修改时间
	private String modifier;	//修改人
	private String serialNumber;	//用户手机号码
	private PicAttr attr;		//图片属性
	private String schoolName;	//归属校区
	private String sessionKey;	//所有需要登录才能访问的接口， 都需要传递sessionKey
	public User(){
		
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(String adminFlag) {
		this.adminFlag = adminFlag;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public PicAttr getAttr() {
		return attr;
	}

	public void setAttr(PicAttr attr) {
		this.attr = attr;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	
}
