package com.rs.teach.mapper.section.entity;

import java.io.Serializable;

/**
* Section.java
* @Description:章节及个人上传课件实体类
* @author: suzhao
* @date: 2019年7月31日 下午1:27:12
* @version: V1.0
*/
public class Section implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6391018433420677203L;
	
	private String upLoadId;	//教师上传文档住建Id
	private String sectionId;	//章节id
	private String totleSectionSortid;	//大章节id
	private String totleSectionSort;	//大章节序号
	private String totleSectionName;	//大章节名
	private String sectionSort;		//章节序号
	private String sectionName;	//章节名
	private String courseId;	//课程资源id
	private String sectionUrl;	//	章节文件保存url
	private String updateUser;	//修改章节的用户id
	private String updateFileName;	//用户上传的文件名称
	private String sectionType;	//文档类型
	private String workId;	//练习id
	private String testPaperId;	//考试id
	private String coursewareId; //课件Id
	private String uploadPath;	//上传文件的映射路径
	
	private String totleSortId;	//大章节id
	
	private String extend1;	//预留字段
	private String extend2;
	private String extend3;
	private String extend4;
	
	public Section(){
		
	}
	public String getTotleSectionSortid() {
		return totleSectionSortid;
	}

	public void setTotleSectionSortid(String totleSectionSortid) {
		this.totleSectionSortid = totleSectionSortid;
	}
	public String getCoursewareId() {
		return coursewareId;
	}

	public void setCoursewareId(String coursewareId) {
		this.coursewareId = coursewareId;
	}
	public String getUpLoadId() {
		return upLoadId;
	}

	public void setUpLoadId(String upLoadId) {
		this.upLoadId = upLoadId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	
	public String getTotleSectionSort() {
		return totleSectionSort;
	}

	public void setTotleSectionSort(String totleSectionSort) {
		this.totleSectionSort = totleSectionSort;
	}

	public String getTotleSectionName() {
		return totleSectionName;
	}

	public void setTotleSectionName(String totleSectionName) {
		this.totleSectionName = totleSectionName;
	}

	public String getSectionSort() {
		return sectionSort;
	}

	public void setSectionSort(String sectionSort) {
		this.sectionSort = sectionSort;
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
	
	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}
	
	public String getTestPaperId() {
		return testPaperId;
	}

	public void setTestPaperId(String testPaperId) {
		this.testPaperId = testPaperId;
	}
	
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
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

	public String getTotleSortId() {
		return totleSortId;
	}

	public void setTotleSortId(String totleSortId) {
		this.totleSortId = totleSortId;
	}
	
	
}