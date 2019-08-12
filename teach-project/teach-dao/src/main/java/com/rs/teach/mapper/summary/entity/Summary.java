package com.rs.teach.mapper.summary.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description  课后总结表
 * @create 2019-08-07 12:55
 */
@Data
public class Summary implements Serializable {

    private static final long serialVersionUID = -3727256124304976730L;

    private String userId; //用户id
    private String sectionSort;  //大章节序号
    private String courseId;   //课程id
    private String classId;    //班级id
    private String summary;    //课程总结笔记
    private String extend1;	//预留字段
    private String extend2;	//
    private String extend3;	//
    private String extend4;	//
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSectionSort() {
		return sectionSort;
	}
	public void setSectionSort(String sectionSort) {
		this.sectionSort = sectionSort;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
