package com.rs.teach.mapper.studyAttr.entity;

import java.io.Serializable;

/**
* NoteSummary.java
* @Description:课后笔记总结实体类
* @author: suzhao
* @date: 2019年7月30日 下午4:40:33
* @version: V1.0
*/
public class NoteSummary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9010432696683535809L;
	
	private String userId; 	//用户id
	private String sectionId;	//章节id
	private String courseId;	//课程资源id
	private String classId;	//班级id
	private String note;	//课后笔记
	private String summary;		//课后总结
	private String extends1;	//预留字段1
	private String extends2;	//预留字段2
	private String extends3;	//预留字段3
	private String extends4;	//预留字段4
	
	public NoteSummary() {
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getExtends1() {
		return extends1;
	}

	public void setExtends1(String extends1) {
		this.extends1 = extends1;
	}

	public String getExtends2() {
		return extends2;
	}

	public void setExtends2(String extends2) {
		this.extends2 = extends2;
	}

	public String getExtends3() {
		return extends3;
	}

	public void setExtends3(String extends3) {
		this.extends3 = extends3;
	}

	public String getExtends4() {
		return extends4;
	}

	public void setExtends4(String extends4) {
		this.extends4 = extends4;
	}
	
	
	
}