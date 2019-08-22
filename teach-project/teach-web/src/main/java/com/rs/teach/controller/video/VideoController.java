package com.rs.teach.controller.video;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.service.video.VideoService;

/**
* VideoController.java
* @Description:视频资源controller
* @author: suzhao
* @date: 2019年8月21日 下午4:15:46
* @version: V1.0
*/
@Controller
@RequestMapping(value = "/video")
public class VideoController{
	
	@Autowired
	private VideoService videoService;
	
	/**
	* 初始化视频课程资源
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月21日 下午4:23:16
	*/
	@RequestMapping("/initVideo")
	@ResponseBody
	public ResponseBean initVideo(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		String videoType = request.getParameter("videoType");
		
		//初始化分页信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		List<Video> list = videoService.getVideos(videoType);
		
		PageInfo<Video> pageInfo = new PageInfo<Video>(list, 9);
		
		bean.addSuccess(pageInfo);
		return bean;
	}
	
	
	
}