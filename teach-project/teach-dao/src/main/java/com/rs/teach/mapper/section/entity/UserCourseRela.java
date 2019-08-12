package com.rs.teach.mapper.section.entity;

import com.rs.teach.mapper.common.Enums.CourseStatusEnum;
import lombok.Data;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 13:24
 */
@Data
public class UserCourseRela {

    //课程id
    private String courseId;

    //用户id
    private String userId;

    //章节id
    private String sectionId;

    /**
     *  NO_SIGN(0, "待报名"),
     *  STARTING(1, "学习中"),
     *  END(2, "已学完");
     *  {@link CourseStatusEnum}
     *
     */
    private String isFinish;

    //关联类型（0：章节，1：课程 , 2: 课程取消）
    private String relaType;

    //班级Id
    private String classId;

    //预留字段
    private String extend1;
    private String extend2;
    private String extend3;
    private String extend4;
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
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
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public String getRelaType() {
		return relaType;
	}
	public void setRelaType(String relaType) {
		this.relaType = relaType;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
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
