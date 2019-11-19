package com.rs.teach.service.studyAttr;

import java.util.List;

import com.rs.teach.mapper.studyAttr.entity.StudyTeam;

/**
* StudyTeamService.java
* @Description:
* @author: suzhao
* @date: 2019年7月29日 上午10:22:33
* @version: V1.0
*/
public interface StudyTeamService{
	/**
	* 根据userId查询用户所属校区的班级信息
	* @param userId
	* @throws
	* @return List
	* @author suzhao
	* @date 2019年7月29日 上午10:25:44
	*/
	public List<StudyTeam> getClassById(String userId);
	
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