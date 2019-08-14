package com.rs.teach.service.section.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.section.dao.SectionMapper;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.service.section.SectionService;

@Service
public class SectionServiceImpl implements SectionService{
	
	@Autowired
	private SectionMapper mapper;
	
	@Override
	public List<Section> getSectionByUser(String userId, String sectionId) {
		return mapper.querySectionByUser(userId, sectionId);
	}

	@Override
	public Section getSectionById(String sectionId) {
		return mapper.querySection(sectionId);
	}

	@Override
	public int addTeachUpSection(Section section) {
		return mapper.insertTeachUpSection(section);
	}

	@Override
	public Section getUpLoadSection(String upLoadId) {
		return mapper.queryUpLoadSection(upLoadId);
	}

	@Override
	public List<Section> getSectionByCourseId(String courseId) {
		return mapper.querySectionByCourseId(courseId);
	}

	@Override
	public List<Map<String, Object>> getSectionStatus(String courseId, String userId, String classId) {
		return mapper.querySectionStatus(courseId, userId, classId);
	}
	
	
}