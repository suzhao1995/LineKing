package com.rs.teach.mapper.timeTable.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.timeTable.entity.Schedule;

/**
* ScheduleMapper.java
* @Description:课表dao层接口
* @author: suzhao
* @date: 2019年7月23日 下午4:42:47
* @version: V1.0
*/
public interface ScheduleMapper{
	
	/**
	* 根据用户id查询用户所有课表
	* @param userId
	* @throws
	* @return list
	* @author suzhao
	* @date 2019年7月23日 下午4:45:40
	*/
	public List<Schedule> querySchedulesByUserId(String userId);
	
	/**
	* 添加个人课表
	* @param schedule
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月24日 下午6:06:29
	*/
	public int insertSchedule(Schedule schedule);
	
	/**
	* 修改个人课表
	* @param schedule
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月30日 上午10:20:42
	*/
	public int updateSchedule(Schedule schedule);
	
	/**
	* 查询用户所教班级
	* @param userId
	* @throws
	* @return List<StudyTeam>
	* @author suzhao
	* @date 2019年7月30日 上午11:45:22
	*/
	public List<Map<String,Object>> queryStudyTeamById(String userId);
	
}