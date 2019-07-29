package com.rs.teach.service.studyAttr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.studyAttr.dao.CourseMapper;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.service.studyAttr.CourseService;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseMapper mapper;
	
	@Override
	public List<Course> getCourseByUserId(String userId) {
		return mapper.queryCourseById(userId);
	}
	
}