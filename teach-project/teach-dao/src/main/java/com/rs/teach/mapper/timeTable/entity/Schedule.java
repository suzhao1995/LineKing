package com.rs.teach.mapper.timeTable.entity;

import java.io.Serializable;

/**
* Schedule.java
* @Description:课表实体类
* @author: suzhao
* @date: 2019年7月23日 下午4:04:02
* @version: V1.0
*/
public class Schedule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6050698270693638574L;
	
	private String teachUserId;	//用户id
	private String teachUserName;	//用户名称
	private String curriculum;	//课程名称
	private String startDate;	//课程开始时间 例如：14:00
	private String endDate;		//课程结束时间
	private int weekDay;	//weekDay？	例如：周一
	private String className;	//授课班级
	private String extend1;	//预留字段1
	private String extend2;	//预留字段2
	private String extend3;	//预留字段3
	private String extend4;	//预留字段4
	
	public Schedule(){
		
	}

	public String getTeachUserId() {
		return teachUserId;
	}

	public void setTeachUserId(String teachUserId) {
		this.teachUserId = teachUserId;
	}

	public String getTeachUserName() {
		return teachUserName;
	}

	public void setTeachUserName(String teachUserName) {
		this.teachUserName = teachUserName;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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