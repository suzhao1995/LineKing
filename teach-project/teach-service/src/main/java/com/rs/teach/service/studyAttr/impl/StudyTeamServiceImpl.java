package com.rs.teach.service.studyAttr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.studyAttr.dao.StudyTeamMapper;
import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.service.studyAttr.StudyTeamService;

@Service
public class StudyTeamServiceImpl implements StudyTeamService{
	
	@Autowired
	private StudyTeamMapper mapper;
	
	@Override
	public List<StudyTeam> getClassById(String userId) {
		return mapper.queryClassByUserId(userId);
	}

	@Override
	public void addStudyTeam(StudyTeam team) {
		mapper.addStudyTeam(team);
	}
	
}