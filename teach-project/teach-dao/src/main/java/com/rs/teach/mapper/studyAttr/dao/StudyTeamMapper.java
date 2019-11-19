package com.rs.teach.mapper.studyAttr.dao;

import java.util.List;

import com.rs.teach.mapper.studyAttr.entity.StudyTeam;

public interface StudyTeamMapper{
	/**
	* 根据userId查询所属校区班级
	* @param
	* @throws
	* @return
	* @author suzhao
	* @date 2019年7月26日 上午11:22:21
	*/
	public List<StudyTeam> queryClassByUserId(String userId);
	
	/**
	* 新增班级
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年11月19日 上午11:25:42
	*/
	public void addStudyTeam(StudyTeam team);
}