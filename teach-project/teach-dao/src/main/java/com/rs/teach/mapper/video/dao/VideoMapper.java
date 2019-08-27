package com.rs.teach.mapper.video.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;
import org.apache.ibatis.annotations.Param;
import com.rs.teach.mapper.video.entity.VideoSection;

/**
* VideoMapper.java
* @Description:视频课程dao
* @author: suzhao
* @date: 2019年8月21日 下午1:03:44
* @version: V1.0
*/
public interface VideoMapper{
	public List<Video> queryVideos(@Param("videoType") String videoType,@Param("schoolId") String schoolId);

    /**
     * 查询电影章节信息
     * @param videoSectionId
     * @return
     */
    VideoSection selectVideoSection(@Param("videoSectionId") String videoSectionId);

    /**
     * 根据视频课主键查询视频章节信息
     * @param videoId
     * @return
     */
    List<Section> queryVideoSectionByVideoId(@Param("videoId") String videoId);

	public List<Map<String,Object>> MyVideo(String userId);

	public String getVideoNum(String videoId);

	public Video queryVideoById(String videoId);

	public List<Map<String, Object>> finishStudy(@Param("userId") String userId, @Param("classId") String classId, @Param("videoId") String videoId);

	public List<VideoSection> queryVideoSection(String videoId);

	public VideoSection querySectionBySecId(String videoSectionId);

	public List<Map<String,Object>> querySectionStatus(@Param("videoId") String videoId, @Param("userId") String userId, @Param("classId") String classId);
}