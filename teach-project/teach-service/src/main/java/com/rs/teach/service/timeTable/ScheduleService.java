package com.rs.teach.service.timeTable;

import java.util.List;

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
}