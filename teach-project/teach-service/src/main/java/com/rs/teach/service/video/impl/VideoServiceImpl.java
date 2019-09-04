package com.rs.teach.service.video.impl;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.video.entity.VideoSection;
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
	public List<Video> getVideos(String videoType, String schoolId) {
		List<Video> list = mapper.queryVideos(videoType, schoolId);
		for(Video video : list){
			String videoNum = mapper.getVideoNum(video.getVideoId());
			video.setVideoNum(videoNum);
		}
		return list;
	}

	@Override
	public List<Map<String,Object>> MyVideo(String userId) {
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
		return mapper.queryVideoSection(videoId);
	}

	@Override
	public VideoSection getSectionBySecId(String videoSectionId) {
		return mapper.querySectionBySecId(videoSectionId);
	}

	@Override
	public List<Map<String, Object>> getSectionStatus(String videoId, String userId, String classId) {
		return mapper.querySectionStatus(videoId, userId, classId);
	}

	@Override
	public List<Section> queryVideoSectionByVideoId(String videoId) {
		return mapper.queryVideoSectionByVideoId(videoId);
	}
	
	//管理员模块 Start
	@Override
	public List<Video> adminGetVideos(String videoType) {
		return mapper.adminQueryVideos(videoType);
	}

	@Override
	public List<Video> adminVideosInit(String videoName, String videoType) {
		return mapper.adminVideosInit(videoName, videoType);
	}

	@Override
	public void adminAddVideo(Video video) {
		mapper.adminInsertVideo(video);
	}

	@Override
	public void adminDelVideo(String videoId) {
		mapper.adminDelVideo(videoId);
	}

	@Override
	public void adminUpdate(Video video) {
		mapper.adminUpdate(video);
	}

	@Override
	public List<Map<String,String>> adminTotleInfo(String videoId) {
		return mapper.adminTotleInfo(videoId);
	}

	@Override
	public List<Map<String, Object>> adminGetVideoInfo(String videoId) {
		return mapper.adminGetVideoInfo(videoId);
	}

	@Override
	public int adminAddTotleInfo(TotleSection totleSection) {
		return mapper.adminAddTotleInfo(totleSection);
	}

	@Override
	public TotleSection getTotleSection(String id) {
		return mapper.getTotleSection(id);
	}

	@Override
	public void updateTotleSection(TotleSection totleSection) {
		mapper.updateTotleSection(totleSection);
	}

	@Override
	public List<VideoSection> adminGetVedioSection(String totleSortId, String videoId) {
		return mapper.adminGetVedioSection(totleSortId, videoId);
	}

	@Override
	public void insertVideoSection(VideoSection videoSection) {
		mapper.insertVideoSection(videoSection);
	}

	@Override
	public void delVideoSection(String videoSectionId) {
		mapper.delVideoSection(videoSectionId);
	}

	@Override
	public void updateVideoSection(VideoSection videoSection) {
		mapper.updateVideoSection(videoSection);
	}

}