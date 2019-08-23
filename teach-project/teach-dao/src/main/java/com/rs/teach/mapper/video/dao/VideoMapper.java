package com.rs.teach.mapper.video.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;

/**
* VideoMapper.java
* @Description:视频课程dao
* @author: suzhao
* @date: 2019年8月21日 下午1:03:44
* @version: V1.0
*/
public interface VideoMapper{
	public List<Video> queryVideos(String videoType);
	
	public List<Video> MyVideo(String userId);
	
	public String getVideoNum(String videoId);
	
	public Video queryVideoById(String videoId);
	
	public List<Map<String, Object>> finishStudy(@Param("userId") String userId, @Param("classId") String classId, @Param("videoId") String videoId);

	public List<VideoSection> queryVideoSection(String videoId);
}