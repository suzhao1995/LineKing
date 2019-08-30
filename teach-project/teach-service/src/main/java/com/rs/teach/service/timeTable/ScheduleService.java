package com.rs.teach.service.timeTable;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.timeTable.entity.Schedule;

/**
* ScheduleService.java
* @Description:课表service
* @author: suzhao
* @date: 2019年7月23日 下午4:48:46
* @version: V1.0
*/
public interface ScheduleService{
	/**
	* 根据userId查询用户所有课表信息
	* @param userId
	* @throws
	* @return list
	* @author suzhao
	* @date 2019年7月23日 下午4:50:12
	*/
	public List<Schedule> getSchedulesByUserId(String userId);
	
	/**
	* 添加个人课表
	* @param schedule
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月24日 下午6:06:29
	*/
	public int addSchedule(Schedule schedule);
	
	
	/**
	* 修改个人课表
	* @param schedule
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月30日 上午10:17:27
	*/
	public int modifySchedule(Schedule schedule);
	
	/**
	* 查询用户课表所教班级
	* @param userId
	* @throws
	* @return List<StudyTeam>
	* @author suzhao
	* @date 2019年7月30日 上午11:43:34
	*/
	public List<Map<String,Object>> getStudyTeamByUserId(String userId);
	
	/**
	* 删除课表
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年8月13日 上午11:16:53
	*/
	public void delSchedule(String scheduleId);
	
	/**
	* 查询用户课程所绑定的班级
	* @param 
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年8月30日 下午5:19:09
	*/
	public List<Map<String,Object>> getClassIdByUserId(String userId);
}