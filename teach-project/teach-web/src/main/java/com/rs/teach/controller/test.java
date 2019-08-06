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
import com.rs.teach.service.User.UserService;


@Controller
@RequestMapping(value="/test")
public class test {
	@Autowired
	private UserService userService;
	
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
}
