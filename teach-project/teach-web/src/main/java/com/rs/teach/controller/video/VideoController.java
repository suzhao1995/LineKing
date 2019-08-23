package com.rs.teach.controller.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;
import com.rs.teach.service.studyAttr.StudyTeamService;
import com.rs.teach.service.training.UserCourseRelaService;
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
	private static final Logger logger = Logger.getLogger(VideoController.class);
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private StudyTeamService studyTeamService;
	
	@Autowired
	private UserCourseRelaService userCourseRelaService;
	
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
		
		//获取用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		String videoType = request.getParameter("videoType");
		
		//初始化分页信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		List<Video> list = videoService.getVideos(videoType);
		
		//查询属于我的课程的信息
		List<Video>	MyVideo = videoService.MyVideo(userId);
		for(Video video : list){
			MyCourseToList(video,MyVideo);
		}
		PageInfo<Video> pageInfo = new PageInfo<Video>(list, 9);
		
		bean.addSuccess(pageInfo);
		return bean;
	}
	
	/**
	* 初始化单个视频课程的课件信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 上午10:57:34
	*/
	@RequestMapping("/initVideoSection")
	@ResponseBody
	public ResponseBean initVideoSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		//获取用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		//获取请求参数
		String videoId = request.getParameter("videoId");
		
		Video video = videoService.getVideoById(videoId);
		ajaxData.put("video", video);	//课程资源信息
		
		//查询课程的具体章节信息
		List<VideoSection> Videos = videoService.getVideoSection(videoId);
		
		List<TrainSectionVo> videoList = groupByToleSort(Videos);
		
		ajaxData.put("videoList", videoList);
		//查询属于我的课程的信息
		List<Video>	MyVideo = videoService.MyVideo(userId);
		for(Video video1 : MyVideo){
			if(video1.getVideoId().equals(video.getVideoId())){
				ajaxData.put("videoStatus", "1");	//1：已添加到我的课程
			}
		}
		//查询用户所属校区班级
		List<StudyTeam> teams = studyTeamService.getClassById(userId);
		ajaxData.put("teams", teams);
		
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 视频课程添加到我的课程
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月12日 下午2:41:28
	*/
	@RequestMapping("/addVideo")
	@ResponseBody
	public ResponseBean addMyCourse(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		//获取请求参数
		String courseId = request.getParameter("courseId");
		String classId = request.getParameter("classId");
		
		try {
			//判断课程是否添加过我的课程
			int count = userCourseRelaService.isAddCourse(courseId, userId, classId);
			if(count > 0){
				//修改relaType
				userCourseRelaService.modifyRelaType(courseId, userId, classId,"2");
				bean.addSuccess();
				return bean;
			}
			//添加课程信息
			int result = userCourseRelaService.addCourse(courseId, userId, classId,"3");
			if(result == 1){
				userCourseRelaService.addAllSection(courseId, userId, classId,"4");
				bean.addSuccess();
			}
		} catch (Exception e) {
			logger.error("--------系统异常--------", e);
			bean.addDefaultError();
		}
		return bean;
	}
	
	/**
	* 将属于我的课程添加到课程列表
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年8月12日 上午10:03:40
	*/
	public void MyCourseToList(Video video, List<Video> myVideo){
		
		for(int i = 0; i <myVideo.size(); i++){
			//将属于我的课程添加进list
			if(video.getVideoId().equals(myVideo.get(i).getVideoId())){
				video.setIsBelongMe("true");
			}
		}
	}
	
	/*
	 * 按大章节目录进行分组
	 * */
	public List<TrainSectionVo> groupByToleSort(List<VideoSection> list){
		List<TrainSectionVo> sectionList = new ArrayList<TrainSectionVo>();
		//按大章节目录进行分组
		Map<String,List<VideoSection>> map = new HashMap<String,List<VideoSection>>();
		for(VideoSection videoSection : list){
			List<VideoSection> tmpList = map.get(videoSection.getVideoTotleSort());
			if(tmpList == null){
				tmpList = new ArrayList<VideoSection>();
				tmpList.add(videoSection);
				map.put(videoSection.getVideoTotleSort(), tmpList);
			}else{
				tmpList.add(videoSection);
			}
		}
		Set set = map.entrySet();
		Iterator<Set> iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, List<VideoSection>> entry = (Entry<String, List<VideoSection>>) iterator.next();
			TrainSectionVo sectionVo = new TrainSectionVo();
			sectionVo.setTrainSectionSort(entry.getKey());
			sectionVo.setTrainSectionName(entry.getValue().get(0).getVideoTotleName());
			sectionVo.setVideoList((entry.getValue()));
			sectionList.add(sectionVo);
		}
		return sectionList;
	}
	
	
}