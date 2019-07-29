package com.rs.teach.mapper.studyAttr.entity;

import java.io.Serializable;

/**
* Course.java
* @Description:课程资源实体
* @author: suzhao
* @date: 2019年7月29日 上午11:06:00
* @version: V1.0
*/
public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6347311080483978215L;
	
	private String courseId;	//课程ID
	private String courseName;	//课程名称
	private String sectionNumber;	//章节总数
	private String coursePicUrl;	//课程资源Url
	private String courseWare;	//是否含有课件（1：是；0否）
	private String schoolWork;	//是否含有作业（1：是；0否）
	private String testPaper;	//是否含有试卷（1：是；0否）
	
	public Course() {
		
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public String getCoursePicUrl() {
		return coursePicUrl;
	}

	public void setCoursePicUrl(String coursePicUrl) {
		this.coursePicUrl = coursePicUrl;
	}

	public String getCourseWare() {
		return courseWare;
	}

	public void setCourseWare(String courseWare) {
		this.courseWare = courseWare;
	}

	public String getSchoolWork() {
		return schoolWork;
	}

	public void setSchoolWork(String schoolWork) {
		this.schoolWork = schoolWork;
	}

	public String getTestPaper() {
		return testPaper;
	}

	public void setTestPaper(String testPaper) {
		this.testPaper = testPaper;
	}
	
	
	
	
}