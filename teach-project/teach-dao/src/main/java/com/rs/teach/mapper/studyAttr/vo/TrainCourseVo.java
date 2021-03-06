package com.rs.teach.mapper.studyAttr.vo;

import com.rs.teach.mapper.section.vo.TrainSectionVo;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	private Integer trainSectionNumber;	//培训章节总数
	private String trainCoursePicUrl;	//培训课程封面URL
	private String addTime;      			//培训课程添加时间
	private List<TrainSectionVo> trainSectionVoList;    //课程下的大章节集合
}