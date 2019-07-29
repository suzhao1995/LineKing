package com.rs.teach.mapper.studyAttr.entity;

import java.io.Serializable;

/**
* StudyTeam.java
* @Description:班级实体
* @author: suzhao
* @date: 2019年7月26日 上午11:16:17
* @version: V1.0
*/
public class StudyTeam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8654019082729657780L;
	
	private String classId;	//班级id
	private String className;	//班级名称
	private String schoolName;	//学校名称
	private String extends1;	//预留字段1
	private String extends2;	//
	private String extends3;	//
	private String extends4;	//
	
	public StudyTeam() {
		
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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