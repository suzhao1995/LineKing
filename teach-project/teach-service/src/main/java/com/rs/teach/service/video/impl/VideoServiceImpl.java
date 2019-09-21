package com.rs.teach.service.video.impl;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.studyAttr.dao.TestAndWorkMapper;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.video.entity.VideoSection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rs.teach.mapper.video.dao.VideoMapper;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;
import com.rs.teach.service.video.VideoService;

@Service
public class VideoServiceImpl implements VideoService{
	private Logger logger = Logger.getLogger(VideoServiceImpl.class);
	@Autowired
	private VideoMapper mapper;
	
	@Autowired
    private TestAndWorkMapper testAndWorkMapper;
	
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
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void insertVideoSection(VideoSection videoSection,Practice work, Testpaper test) {
		try {
			
			mapper.insertVideoSection(videoSection);
			if(work != null){
				testAndWorkMapper.insertPractice(work);
			}
			if(test != null){
				testAndWorkMapper.insertTestpaper(test);
			}
		} catch (Exception e) {
			logger.error("---上传视频失败---");
			throw e;
		}
	}

	@Override
	public void delVideoSection(String videoSectionId) {
		mapper.delVideoSection(videoSectionId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateVideoSection(VideoSection oldSection,VideoSection videoSection,Practice work, Testpaper test) {
		try {
			
			mapper.updateVideoSection(videoSection);
			if(StringUtils.isNotEmpty(videoSection.getWorkId())){
				//插入新作业文件
				testAndWorkMapper.insertPractice(work);
				//删除旧作业文件
				if(StringUtils.isNotEmpty(oldSection.getWorkId())){
					testAndWorkMapper.delWorkByWorkId(oldSection.getWorkId());
				}
			}
			if(StringUtils.isNotEmpty(videoSection.getPaperId())){
				//插入试卷文件
				testAndWorkMapper.insertTestpaper(test);
				//删除旧作业文件
				if(StringUtils.isNotEmpty(oldSection.getPaperId())){
					testAndWorkMapper.delTestByTestId(oldSection.getPaperId());
				}
			}
		} catch (Exception e) {
			logger.error("---修改视频失败---");
			throw e;
		}
	}

    @Override
    public Integer selectVideoNum() {
        return mapper.selectVideoNum();
    }

}