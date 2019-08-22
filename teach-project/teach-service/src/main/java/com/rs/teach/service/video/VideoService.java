package com.rs.teach.service.video;

import java.util.List;

import com.rs.teach.mapper.video.entity.Video;

/**
* VideoService.java
* @Description:视频模块service
* @author: suzhao
* @date: 2019年8月21日 下午12:55:31
* @version: V1.0
*/
public interface VideoService{
	
	/**
	* 根据视频分类查询视频资源
	* @param 
	* @throws
	* @return List<Video>
	* @author suzhao
	* @date 2019年8月21日 下午4:44:34
	*/
	public List<Video> getVideos(String videoType);
}