package com.rs.teach.mapper.video.entity;

import java.io.Serializable;

/**
* Video.java
* @Description:视频课程entity
* @author: suzhao
* @date: 2019年8月21日 下午1:02:47
* @version: V1.0
*/
public class Video implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4373198101238500412L;
	
	private String videoId;		//视频课程id
	
	private String videoName;	//视频课程名称
	
	private String videoUrl;	//视频课程封面
	
	private String schoolWork;	//是否含有作业（1：是；0：否）
	
	private String videoWare;	//是否含有视频课件（1：是；0：否）
	
	private String testPaper;	//是否含有试卷（1：是；0：否）
	
	private String videoType;	//视频分类
	
	private String isBelongMe;	//是否属于我的课程
	
	private String videoNum;	//总章节数
	
	public Video(){
		
	}
	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getSchoolWork() {
		return schoolWork;
	}

	public void setSchoolWork(String schoolWork) {
		this.schoolWork = schoolWork;
	}

	public String getVideoWare() {
		return videoWare;
	}

	public void setVideoWare(String videoWare) {
		this.videoWare = videoWare;
	}

	public String getTestPaper() {
		return testPaper;
	}

	public void setTestPaper(String testPaper) {
		this.testPaper = testPaper;
	}

	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}

	public String getIsBelongMe() {
		return isBelongMe;
	}
	public void setIsBelongMe(String isBelongMe) {
		this.isBelongMe = isBelongMe;
	}
	public String getVideoNum() {
		return videoNum;
	}
	public void setVideoNum(String videoNum) {
		this.videoNum = videoNum;
	}
	
	
	
}