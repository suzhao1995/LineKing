package com.rs.teach.service.video.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.video.dao.VideoMapper;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;
import com.rs.teach.service.video.VideoService;

@Service
public class VideoServiceImpl implements VideoService{
	
	@Autowired
	private VideoMapper mapper;
	
	@Override
	public List<Video> getVideos(String videoType) {
		List<Video> list = mapper.queryVideos(videoType);
		for(Video video : list){
			String videoNum = mapper.getVideoNum(video.getVideoId());
			video.setVideoNum(videoNum);
		}
		return list;
	}

	@Override
	public List<Video> MyVideo(String userId) {
		return mapper.MyVideo(userId);
	}

	@Override
	public Video getVideoById(String videoId) {
		Video video = mapper.queryVideoById(videoId);
		String videoNum = mapper.getVideoNum(video.getVideoId());
		video.setVideoNum(videoNum);
		return video;
	}

	@Override
	public List<Map<String, Object>> getFinishStudy(String userId, String classId, String videoId) {
		return mapper.finishStudy(userId, classId, videoId);
	}

	@Override
	public List<VideoSection> getVideoSection(String videoId) {
		
		return null;
	}
	
}