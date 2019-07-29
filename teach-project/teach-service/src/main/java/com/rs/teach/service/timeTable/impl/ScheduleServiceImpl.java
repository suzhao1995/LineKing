package com.rs.teach.service.timeTable.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
