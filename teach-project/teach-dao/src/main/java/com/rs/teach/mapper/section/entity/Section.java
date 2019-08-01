package com.rs.teach.mapper.section.entity;

import java.io.Serializable;

/**
* Section.java
* @Description:章节实体类
* @author: suzhao
* @date: 2019年7月31日 下午1:27:12
* @version: V1.0
*/
public class Section implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6391018433420677203L;
	
	private String sectionId;	//章节id
	private String sectionName;	//章节名
	private String courseId;	//课程资源id
	private String sectionUrl;	//	章节文件保存url
	private String updateUser;	//修改章节的用户id
	private String updateFileName;	//用户上传的文件名称
	private String sectionType;	//文档类型
	private String extend1;	//预留字段
	private String extend2;
	private String extend3;
	private String extend4;
	
	public Section(){
		
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSectionUrl() {
		return sectionUrl;
	}

	public void setSectionUrl(String sectionUrl) {
		this.sectionUrl = sectionUrl;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateFileName() {
		return updateFileName;
	}

	public void setUpdateFileName(String updateFileName) {
		this.updateFileName = updateFileName;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
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