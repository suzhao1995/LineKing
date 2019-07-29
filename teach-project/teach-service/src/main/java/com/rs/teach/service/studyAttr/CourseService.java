package com.rs.teach.service.studyAttr;

import java.util.List;

import com.rs.teach.mapper.studyAttr.entity.Course;

/**
* CourseService.java
* @Description:课程资源service
* @author: suzhao
* @date: 2019年7月29日 上午11:15:57
* @version: V1.0
*/
public interface CourseService{
	/**
	* 查询当前用户所教课程
	* @param userId
	* @throws
	* @return
	* @author suzhao
	* @date 2019年7月29日 上午11:16:41
	*/
	public List<Course> getCourseByUserId(String userId);
}