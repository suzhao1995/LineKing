package com.rs.teach.mapper.video.entity;

import lombok.Data;

import java.io.Serializable;

/**
* VideoSection.java
* @Description:视频资源课件
* @author: suzhao
* @date: 2019年8月21日 下午1:03:27
* @version: V1.0
*/
@Data
public class VideoSection implements Serializable{

	private static final long serialVersionUID = -8509058569897494920L;
	
	private String videoSectionId;	//章节id(主键)
	
	private String videoSectionName;	//章节名
	
	private String videoId;		//视频课程id
	
	private String videoSectionUrl;		//章节路径（服务器路径）

	private String videoSectionPath;    //章节绝对路径
	
	private String videoSectionSort;	//章节序号
	
	private String videoTotleSort;	//大章节序号
	
	private String videoTotleName;	//大章节名
	
	private String videoTotleSortId;	//大章节序号id
	
	private String paperId;		//试卷ID
	
	private String workId;	//作业ID
	
	private String courseWareId;	//课件id
	
}