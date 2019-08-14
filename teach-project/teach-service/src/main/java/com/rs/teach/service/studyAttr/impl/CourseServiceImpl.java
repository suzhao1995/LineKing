package com.rs.teach.service.studyAttr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.studyAttr.dao.CourseMapper;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.NoteSummary;
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
	public List<Map<String, Object>> getNoteSummary(String userId, String classId, String courseId, String code, String sectionId) {
		Map<String,String> con_map = new HashMap<String,String>();
		con_map.put("con_userId", userId);
		con_map.put("con_classId", classId);
		con_map.put("con_courseId", courseId);
		con_map.put("con_sectionId", sectionId);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if("0".equals(code)){
			list = mapper.queryNote(con_map);
		}else if("1".equals(code)){
			list = mapper.querySummary(con_map);
		}
		return list;
	}

	@Override
	public boolean isExsitSummary(String userId, String sectionId,String classId) {
		int count = mapper.isExsitSummary(userId, sectionId,classId);
		if(count == 1){
			return true;
		}
		return false;
	}

	@Override
	public int modifySummary(NoteSummary noteSummary) {
		return mapper.updateSummary(noteSummary);
	}

	@Override
	public int addSummary(NoteSummary noteSummary) {
		return mapper.insertSummary(noteSummary);
	}

	@Override
	public List<Course> getCourse(String courseType, String schoolId, String courseLev, String likeSearch) {
		Map<String,String> conMap = new HashMap<String,String>();
		conMap.put("con_courseType", courseType);
		conMap.put("con_schoolId", schoolId);
		conMap.put("con_courseLev", courseLev);
		conMap.put("con_likeSearch", likeSearch);
		List<Course> list = mapper.queryCourse(conMap);
		return list;
	}

	@Override
	public Course queryCourseByCourseId(String courseId) {
		return mapper.selectCourseByCourseId(courseId);
	}

	@Override
	public boolean isExsitNote(String userId, String sectionId, String classId) {
		int count = mapper.isExsitNote(userId, sectionId, classId);
		if(count == 1){
			return true;
		}
		return false;
	}

	@Override
	public int updateNote(NoteSummary noteSummary) {
		return mapper.updateNote(noteSummary);
	}

	@Override
	public int insertNote(NoteSummary noteSummary) {
		return mapper.insertNote(noteSummary);
	}
	
}