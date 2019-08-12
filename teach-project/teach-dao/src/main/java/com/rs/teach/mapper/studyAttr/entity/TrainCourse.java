package com.rs.teach.mapper.studyAttr.entity;

import com.rs.teach.mapper.common.PageDto;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* Course.java
* @Description:培训课程资源实体
* @author: suzhao
* @date: 2019年7月29日 上午11:06:00
* @version: V1.0
*/
@Data
@ToString
public class TrainCourse extends PageDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3061966659112889238L;


	private String trainCourseId;	//培训课程ID
	private String trainCourseName;	//培训课程名称
	private String trainSectionNumber;	//培训章节总数
	private String trainCoursePicUrl;	//培训课程资源Url
	private String practiceId;	//作业id
	private String testpaperId;	//试卷ID
	private String extend1;	//预留字段
	private String extend2;	//预留字段
	private String extend3;	//预留字段
	private String extend4;	//预留字段
}