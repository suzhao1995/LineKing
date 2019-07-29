package com.rs.teach.mapper.timeTable.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
}