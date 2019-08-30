package com.rs.teach.service.timeTable.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.timeTable.dao.ScheduleMapper;
import com.rs.teach.mapper.timeTable.entity.Schedule;
import com.rs.teach.service.timeTable.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleMapper dao;
	
	@Override
	public List<Schedule> getSchedulesByUserId(String userId) {
		return dao.querySchedulesByUserId(userId);
	}

	@Override
	public int addSchedule(Schedule schedule) {
		return dao.insertSchedule(schedule);
	}

	@Override
	public int modifySchedule(Schedule schedule) {
		return dao.updateSchedule(schedule);
	}

	@Override
	public List<Map<String,Object>> getStudyTeamByUserId(String userId) {
		return dao.queryStudyTeamById(userId);
	}

	@Override
	public void delSchedule(String scheduleId) {
		dao.deleteSchedule(scheduleId);
	}

	@Override
	public List<Map<String, Object>> getClassIdByUserId(String userId) {
		return dao.queryClassIdByUserId(userId);
	}

}
