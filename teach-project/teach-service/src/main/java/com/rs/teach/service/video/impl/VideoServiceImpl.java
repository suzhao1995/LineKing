package com.rs.teach.service.video.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.video.dao.VideoMapper;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.service.video.VideoService;

@Service
public class VideoServiceImpl implements VideoService{
	
	@Autowired
	private VideoMapper mapper;
	
	@Override
	public List<Video> getVideos(String videoType) {
		return null;
	}
	
}