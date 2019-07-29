package com.rs.teach.mapper.studyAttr.dao;

import java.util.List;

import com.rs.teach.mapper.studyAttr.entity.Course;

public interface CourseMapper{
	/**
	* 查询当前用户所教课程
	* @param userId
	* @throws
	* @return
	* @author suzhao
	* @date 2019年7月29日 上午11:08:27
	*/
	public List<Course> queryCourseById(String userId);
}