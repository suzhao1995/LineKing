package com.rs.teach.mapper.video.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rs.teach.mapper.backstage.entity.TotleSection;
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
	
	//管理员Start
	public List<Video> adminQueryVideos(String videoType);
	
	public List<Video> adminVideosInit(@Param("videoName") String videoName, @Param("videoType") String videoType);
	
	public void adminInsertVideo(Video video);
	
	public void adminDelVideo(String videoId);
	
	public void adminUpdate(Video video);
	
	public List<Map<String,String>> adminTotleInfo(String videoId);
	
	public List<Map<String,Object>> adminGetVideoInfo(String videoId);
	
	public int adminAddTotleInfo(TotleSection totleSection);
	
	public TotleSection getTotleSection(String id);
	
	public void updateTotleSection(TotleSection totleSection);
	
	public List<VideoSection> adminGetVedioSection(@Param("totleSortId") String totleSortId, @Param("videoId") String videoId);
	
	public void insertVideoSection(VideoSection videoSection);
	
	public void delVideoSection(String videoSectionId);
	
	public void updateVideoSection(VideoSection videoSection);
	
}