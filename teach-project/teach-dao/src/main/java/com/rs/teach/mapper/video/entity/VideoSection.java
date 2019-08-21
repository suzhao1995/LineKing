package com.rs.teach.mapper.video.entity;

import java.io.Serializable;

/**
* VideoSection.java
* @Description:视频资源课件
* @author: suzhao
* @date: 2019年8月21日 下午1:03:27
* @version: V1.0
*/
public class VideoSection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7064879483600937565L;
	
	private String videoSectionId;	//
	
	private String videoSectionName;	//
	
	private String videoId;		//
	
	private String videoSectionUrl;		//
	
	private String videoSectionSort;	//
	
	private String videoTotleSort;	//
	
	private String videoTotleName;	//
	
	private String paperId;		//
	
	private String workId;	//
	
	private String courseWareId;	//
	
	public VideoSection(){
		
	}

	public String getVideoSectionId() {
		return videoSectionId;
	}

	public void setVideoSectionId(String videoSectionId) {
		this.videoSectionId = videoSectionId;
	}

	public String getVideoSectionName() {
		return videoSectionName;
	}

	public void setVideoSectionName(String videoSectionName) {
		this.videoSectionName = videoSectionName;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoSectionUrl() {
		return videoSectionUrl;
	}

	public void setVideoSectionUrl(String videoSectionUrl) {
		this.videoSectionUrl = videoSectionUrl;
	}

	public String getVideoSectionSort() {
		return videoSectionSort;
	}

	public void setVideoSectionSort(String videoSectionSort) {
		this.videoSectionSort = videoSectionSort;
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

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getCourseWareId() {
		return courseWareId;
	}

	public void setCourseWareId(String courseWareId) {
		this.courseWareId = courseWareId;
	}
	
	
}