package com.rs.teach.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rs.common.utils.Pdf2ImageUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.video.entity.VideoSection;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.video.VideoService;


@Controller
@RequestMapping(value="/test")
public class test {
	@Autowired
	private UserService userService;
	
	@Autowired
	private VideoService videoService;
	
	@RequestMapping("/hello")
	@ResponseBody
	public String getUser(){
		System.out.println(userService.test());
		return "hello";
	}
	
	@RequestMapping("/getImage")
	@ResponseBody
	public ResponseBean getImage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseBean bean = new ResponseBean();
		String url = request.getParameter("url");
		String fileName = request.getParameter("fileName");
		String upLoadId = request.getParameter("upLoadId");
		
		fileName = upLoadId+"_"+fileName;
		Map<String,Object> resultMap = Pdf2ImageUtil.pdf2png(request, url, fileName, "png");
		
		bean.addSuccess(resultMap);
		return bean;
	}
	
	@RequestMapping("/test1")
	@ResponseBody
	public ResponseBean verifytest1(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseBean bean = new ResponseBean();
		VideoSection v = new VideoSection();
		v.setVideoSectionId("1");
		v.setVideoSectionName("test");
		v.setVideoId("1");
		v.setVideoSectionPath("1");
		v.setVideoSectionSort("1");
		v.setVideoSectionUrl("1");
		v.setVideoTotleSortId("1");
		v.setWorkId("1");
		v.setPaperId("1");
		
		Practice w = new Practice();
		w.setPracticeFileName("1");
		w.setPracticeId("1");
		w.setPracticePath("1");
		w.setPracticeUrl("1");
		
		Testpaper t = new Testpaper();
		t.setTestpaperId("1");
		t.setTestpaperName("1");
		t.setTestpaperPath("1");
		t.setTestpaperUrl("1");
		videoService.insertVideoSection(v, w, t);
		return bean;
	}
}
