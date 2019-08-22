package com.rs.teach.mapper.video.dao;

import java.util.List;

import com.rs.teach.mapper.video.entity.Video;

/**
* VideoMapper.java
* @Description:视频课程dao
* @author: suzhao
* @date: 2019年8月21日 下午1:03:44
* @version: V1.0
*/
public interface VideoMapper{
	public List<Video> queryVideos(String videoType);
}