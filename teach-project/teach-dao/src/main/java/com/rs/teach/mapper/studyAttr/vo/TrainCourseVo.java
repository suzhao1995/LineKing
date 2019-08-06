package com.rs.teach.mapper.studyAttr.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
* Course.java
* @Description:培训课程资源返回类
* @author: suzhao
* @date: 2019年7月29日 上午11:06:00
* @version: V1.0
*/
@Data
@ToString
public class TrainCourseVo implements Serializable {

	private static final long serialVersionUID = 2549993087043743877L;

	private String trainCourseId;	//培训课程ID
	private String trainCourseName;	//培训课程名称
	private String trainSectionNumber;	//培训章节总数
	private String trainCoursePicUrl;	//培训课程文件Url
	private String picUrl;         //培训课程封面URL
}