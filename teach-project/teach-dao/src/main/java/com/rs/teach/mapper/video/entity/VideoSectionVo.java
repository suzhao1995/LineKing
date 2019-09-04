package com.rs.teach.mapper.video.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class VideoSectionVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3539165004169576181L;
	
	private String videoTotleId;
	//培训大章节序号
    private String videoTotleSort;

    //培训大章节名称
    private String videoTotleName;
    
    //包含小章节集合
    private List<Map<String,Object>> videoSectionList;

	public String getVideoTotleId() {
		return videoTotleId;
	}

	public void setVideoTotleId(String videoTotleId) {
		this.videoTotleId = videoTotleId;
	}

	public String getVideoTotleSort() {
		return videoTotleSort;
	}

	public void setVideoTotleSort(String videoTotleSort) {
		this.videoTotleSort = videoTotleSort;
	}

	public String getVideoTotleName() {
		return videoTotleName;
	}

	public void setVideoTotleName(String videoTotleName) {
		this.videoTotleName = videoTotleName;
	}

	public List<Map<String, Object>> getVideoSectionList() {
		return videoSectionList;
	}

	public void setVideoSectionList(List<Map<String, Object>> videoSectionList) {
		this.videoSectionList = videoSectionList;
	}

	
    
}