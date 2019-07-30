package com.rs.teach.service.studyAttr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public List<Map<String, Object>> getCourseInfoForUser(String userId, String classId) {
		return mapper.courseInfoForUser(userId, classId);
	}

	@Override
	public List<Map<String, Object>> getFinishStudy(String userId, String classId, String courseId) {
		return mapper.finishStudy(userId, classId, courseId);
	}

	@Override
	public List<Map<String, Object>> getNoteSummary(String userId, String classId, String courseId) {
		Map<String,String> con_map = new HashMap<String,String>();
		con_map.put("con_userId", userId);
		con_map.put("con_classId", classId);
		con_map.put("con_courseId", courseId);
		return mapper.queryNote(con_map);
	}

	@Override
	public boolean isExsitSummary(String userId, String sectionId,String classId) {
		int count = mapper.isExsitSummary(userId, sectionId,classId);
		if(count == 1){
			return true;
		}
		return false;
	}
	
}