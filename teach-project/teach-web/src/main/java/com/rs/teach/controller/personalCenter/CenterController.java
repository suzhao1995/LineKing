package com.rs.teach.controller.personalCenter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.DateUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.Pdf2ImageUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.NoteSummary;
import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.timeTable.entity.Schedule;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.mapper.video.entity.Video;
import com.rs.teach.mapper.video.entity.VideoSection;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.section.SectionService;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.studyAttr.StudyTeamService;
import com.rs.teach.service.timeTable.ScheduleService;
import com.rs.teach.service.training.UserCourseRelaService;
import com.rs.teach.service.video.VideoService;

/**
* CenterController.java
* @Description:个人中心课件课表controller
* @author: suzhao
* @date: 2019年7月24日 下午3:06:17
* @version: V1.0
*/
@Controller
@RequestMapping(value="/personalCenter")
public class CenterController{
	
	private static final Logger logger = Logger.getLogger(CenterController.class);
	/**
	 * 用户 service
	 * */
	@Autowired
	private UserService userService;
	
	/**
	 * 课表 service
	 * */
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 班级
	 * */
	@Autowired
	private StudyTeamService studyTeamService;
	
	/**
	 * 课程资源 service
	 * */
	@Autowired
	private CourseService courseService;
	
	/**
	 * 章节service
	 * */
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private UserCourseRelaService userCourseRelaService;
	
	@Value("${fileMappingPath}")
	private String fileMappingPath;	//文件映射路径
	
	@Value("${filePath}")
	private String filePath;	//文件存放根目录
	
	@Value("${videoMappingUrl}")
	private String videoMappingUrl;
	
	/**
	* 个人中心课表
	* @param request
	* @param response
	* @throws
	* @return bean
	* @author suzhao
	* @date 2019年7月24日 下午3:13:02
	*/
	@RequestMapping("/scheduleIndex")
	@ResponseBody
	public ResponseBean scheduleIndex(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		String userId = userInfo.get("userId").toString();	
		
		//判断用户是否编辑过个人资料
		boolean flag = userService.isModifyInfo(userId);
		if(!flag){
			ajaxData.put("isEdit", "false");
		}
		//查询课程表
		List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
		ajaxData.put("schedules", schedules);
		//首页问候语
		String helloWord = DateUtil.sayHello(new Date());
		ajaxData.put("helloWord", helloWord);
		
		User user = userService.getUserById(userId);
		if(user.getAttr() != null){
			ajaxData.put("picUrl", user.getAttr().getPicUrl());
		}
		ajaxData.put("endTime", user.getEndDate());
		ajaxData.put("teachUserName", user.getUserName());
		
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 初始化教师信息及课表信息
	* @param request
	* @param response
	* @throws
	* @return
	* @author suzhao
	* @date 2019年7月25日 下午2:52:30
	*/
	@RequestMapping("/initEditHome")
	@ResponseBody
	public ResponseBean initEditHome(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		String userId = userInfo.get("userId").toString();	
		//查询用户所在校区的班级
		List<StudyTeam> teams = studyTeamService.getClassById(userId);
		ajaxData.put("teams", teams);
		
		//查询用户所教课程
		List<Course> courses = courseService.getCourseByUserId(userId);
		ajaxData.put("courses", courses);
		
		//查询课程表
		List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
		ajaxData.put("schedules", schedules);
		
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 保存编辑后的课表
	* @param request
	* @param response
	* @throws
	* @return bean
	* @author suzhao
	* @date 2019年7月24日 下午5:13:23
	*/
	@RequestMapping("/editSchedule")
	@ResponseBody
	public ResponseBean editSchedule(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		//获取用户最新编辑的信息
		String flag = request.getParameter("flag");
		int resultCode = 0;
		Schedule schedule = new Schedule();
		schedule.setTeachUserId(userInfo.get("userId").toString());
		schedule.setWeekDay(Integer.valueOf(request.getParameter("weekDay")));
		schedule.setStartDate(request.getParameter("startDate"));
		schedule.setEndDate(request.getParameter("endDate"));
		schedule.setClassId(request.getParameter("classId"));
		schedule.setCurriculumId(request.getParameter("courseId"));
		//新增班级
		StudyTeam team = new StudyTeam();
		team.setClassId(System.currentTimeMillis()+"");
		team.setClassName(request.getParameter("classId"));
		team.setSchoolName(String.valueOf(userInfo.get("schoolId")));
		studyTeamService.addStudyTeam(team);
		if("0".equals(flag)){
			//新增
			resultCode = scheduleService.addSchedule(schedule);
		}else{
			schedule.setScheduleId(request.getParameter("scheduleId"));
			resultCode = scheduleService.modifySchedule(schedule);
		}
		if(resultCode == 1){
			bean.addSuccess();
		}
		
		return bean;
	}
	
	/**
	* 删除课表
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月13日 上午11:16:16
	*/
	@RequestMapping("/delSchedule")
	@ResponseBody
	public ResponseBean delSchedule(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		String scheduleId = request.getParameter("scheduleId");
		try {
			scheduleService.delSchedule(scheduleId);
		} catch (Exception e) {
			logger.error("--------系统异常-------", e);
			bean.addDefaultError();
		}
		return bean;
	}
	
	/**
	* 初始化我的课程
	* @param request
	* @param response
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年7月30日 上午11:31:08
	*/
	@RequestMapping("/initCourse")
	@ResponseBody
	public ResponseBean initSelfCourse(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		//获取页码值，默认为1
		String pageNum = request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum");
		
		//查询用户所教班级
		List<Map<String,Object>> teams = scheduleService.getClassIdByUserId(userId);
		ajaxData.put("teams", teams);
		String classId = request.getParameter("classId");
		if("all".equals(classId)){
			classId = null;
		}

		/**
		 * 后台管理系统教师查询自己的课表
		 */
		if("not".equals(classId)){
			classId = null;
			userId = request.getParameter("userId");
		}

		//初始化分页信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//查询用户所教各班级课程
		List<Map<String,Object>> list = courseService.getCourseInfoForUser(userId,classId);
		for(Map<String,Object> map : list){
			List<Map<String,Object>> finishSec = new ArrayList<Map<String,Object>>();
			if("1".equals(map.get("relaType"))){
				finishSec = courseService.getFinishStudy(userId, map.get("classId").toString(), map.get("courseId").toString());
			}else if("3".equals(map.get("relaType"))){
				finishSec = videoService.getFinishStudy(userId, map.get("classId").toString(), map.get("courseId").toString());
			}
			int number = finishSec == null ? 0 : finishSec.size();	//已学完章节数量
			map.put("finishNumber", number);
			int totleNum = Integer.valueOf(map.get("sectionNumber").toString());	//课程章节总数
			if(totleNum != 0){
				double doubleDigit = Double.valueOf(getDoubleDigit(number,totleNum));	//进度条
				int percentage = (int)(doubleDigit * 100);	
				
				map.put("percentage", percentage);
			}else{
				map.put("percentage", 0);
			}
		}
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list,9);
		ajaxData.put("speedInfo", pageInfo);
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 初始化我的课程详情  --新增视频课程
	* @param request
	* @param response
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年7月30日 上午11:31:08
	*/
	@RequestMapping("/courseInfo")
	@ResponseBody
	public ResponseBean courseInfo(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		String classId = request.getParameter("classId");
		String courseId = request.getParameter("courseId");
		String relaType = request.getParameter("relaType");
		
		if("3".equals(relaType)){
			//查看视频课程详情
			Video video = videoService.getVideoById(courseId);
			
			List<Map<String,Object>> list = videoService.getSectionStatus(courseId, userId, classId);
			ajaxData.put("video", video);	//视频课程资源信息
			
			List<TrainSectionVo> videoSectionList = groupByTotle(list);
			ajaxData.put("videoSectionList", videoSectionList);
			ajaxData.put("relaType", "3");
			bean.addSuccess(ajaxData);
			
		}else if("1".equals(relaType)){
			//查看普通课件详情
			Course course = courseService.queryCourseByCourseId(courseId);
			
			List<Map<String,Object>> list = sectionService.getSectionStatus(courseId, userId, classId);
			course.setSectionNumber(String.valueOf(list.size()));
			ajaxData.put("course", course);	//课程资源信息
			
			List<TrainSectionVo> sectionList = groupByTotle(list);
			ajaxData.put("sectionList", sectionList);
			ajaxData.put("relaType", "1");
			bean.addSuccess(ajaxData);
		}
		
		return bean;
	}
	
	/**
	* 我修改的课程信息
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @throws IOException 
	* @date 2019年7月30日 下午3:43:12
	*/
	@RequestMapping("/queryModifyCourse")
	@ResponseBody
	public ResponseBean queryModifyCourse(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String sectionId = request.getParameter("sectionId");
		String relaType = request.getParameter("relaType");
		
		if("1".equals("relaType")){
			Section modifySec = sectionService.getSectionById(sectionId);
			ajaxData.put("totleSort", modifySec.getTotleSectionSort());
			ajaxData.put("totleName", modifySec.getTotleSectionName());
			ajaxData.put("sort", modifySec.getSectionSort());
			ajaxData.put("name", modifySec.getSectionName());
			ajaxData.put("sectionId", modifySec.getSectionId());
		}
		if("3".equals(relaType)){
			VideoSection videoSection = videoService.getSectionBySecId(sectionId);
			ajaxData.put("totleSort", videoSection.getVideoTotleSort());
			ajaxData.put("totleName", videoSection.getVideoTotleName());
			ajaxData.put("sort", videoSection.getVideoSectionSort());
			ajaxData.put("name", videoSection.getVideoSectionName());
			ajaxData.put("sectionId", videoSection.getVideoSectionId());
		}
		//查看我修改的课件信息
		List<Section> sections = sectionService.getSectionByUser(userId, sectionId);
		if(sections.size() > 0){
			ajaxData.put("updateSection", sections);
		}
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 我的课程---查看章节详情----新增视频课程
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月14日 下午3:24:32
	*/
	@RequestMapping("/sectionDetail")
	@ResponseBody
	public ResponseBean sectionDetail(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		String classId = request.getParameter("classId");
		String sectionId = request.getParameter("sectionId");
		String courseId = request.getParameter("courseId");
		String sectionStatus = request.getParameter("status");	//课程学习状态
		String relaType = request.getParameter("relaType");	//3：视频课程  1：普通课程
		
		ajaxData.put("classId", classId);
		ajaxData.put("sectionId", sectionId);
		ajaxData.put("courseId", courseId);
		
		if("0".equals(sectionStatus)){
			//修改课程状态
			userCourseRelaService.updateIsFinish(courseId, userId, sectionId, 1,classId);
		}
		//查询课程笔记
		String note = courseService.getNoteSummary(userId, classId, courseId, "0", sectionId);
		if(StringUtils.isNotEmpty(note)){
			ajaxData.put("note", note);
		}else{
			ajaxData.put("note", "0");	//无课程笔记
		}
		if("1".equals(relaType)){
			//普通课程课件
			//查询章节目录
			List<Section> list = sectionService.getSectionByCourseId(courseId);
			List<Map<String,Object>> sectionDir = new ArrayList<Map<String,Object>>();
			for(int i = 0; i < list.size(); i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("dirSectionId", list.get(i).getSectionId());
				map.put("dirSectionSort", list.get(i).getSectionSort());
				map.put("dirTotleSectionSort", list.get(i).getTotleSectionSort());
				map.put("dirSectionName", list.get(i).getSectionName());
				sectionDir.add(map);
				//返回下一课的章节id
				if(sectionId.equals(list.get(i).getSectionId())){
					if(i+1 < list.size()){
						ajaxData.put("nextSectionId", list.get(i+1).getSectionId());
					}else{
						ajaxData.put("nextSectionId", "0");	//课程已上完
					}
				}
			}
			ajaxData.put("sectionDir", sectionDir);	//章节目录
			
			//查询章节详情
			Section section = sectionService.getSectionById(sectionId);
			
			ajaxData.put("section", section);	//本章详情
			
			String fileName = section.getCoursewareId()+"_"+section.getUpdateFileName();
			String savePath = fileMappingPath;
			String fileUrl = savePath +section.getSectionUrl()+"/"+fileName+".pdf";
			ajaxData.put("fileUrl", fileUrl);
			bean.addSuccess(ajaxData);
		}else if("3".equals(relaType)){
			//视频课程课件
			//查询章节目录
			List<VideoSection> list = videoService.getVideoSection(courseId);
			List<Map<String,Object>> sectionDir = new ArrayList<Map<String,Object>>();
			for(int i = 0; i < list.size(); i++){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("dirSectionId", list.get(i).getVideoSectionId());
				map.put("dirSectionName", list.get(i).getVideoSectionName());
				map.put("dirSectionSort", list.get(i).getVideoSectionSort());
				map.put("dirTotleSectionSort", list.get(i).getVideoTotleSort());
				sectionDir.add(map);
				//返回下一课的章节id
				if(sectionId.equals(list.get(i).getVideoSectionId())){
					if(i+1 < list.size()){
						ajaxData.put("nextSectionId", list.get(i+1).getVideoSectionId());
					}else{
						ajaxData.put("nextSectionId", "0");	//课程已上完
					}
				}
			}
			ajaxData.put("sectionDir", sectionDir);	//章节目录
			
			//查询章节详情
			VideoSection videoSection = videoService.getSectionBySecId(sectionId);
			
			ajaxData.put("videoSection", videoSection);	//本章详情

			ajaxData.put("videoUrl", videoSection.getVideoSectionPath());
			bean.addSuccess(ajaxData);
		}
		return bean;
	}
	
	/**
	* 查看课程总结
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月14日 下午4:20:21
	*/
	@RequestMapping("/querySummary")
	@ResponseBody
	public ResponseBean querySummary(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		String classId = request.getParameter("classId");
		String sectionId = request.getParameter("sectionId");
		String courseId = request.getParameter("courseId");
		
		ajaxData.put("classId", classId);
		ajaxData.put("sectionId", sectionId);
		ajaxData.put("courseId", courseId);
		//查询课程总结
		String summary = courseService.getNoteSummary(userId, classId, courseId, "1", sectionId);
		if(StringUtils.isNotEmpty(summary)){
			ajaxData.put("summary", summary);
		}else{
			ajaxData.put("summary", "0");	//无课程总结
		}
		//查询章节详情
		Section section = sectionService.getSectionById(sectionId);
		ajaxData.put("section", section);	//本章详情
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 保存笔记
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月14日 下午4:55:41
	*/
	@RequestMapping("/saveNote")
	@ResponseBody
	public ResponseBean saveNote(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		String note = request.getParameter("note");
		String sectionId = request.getParameter("sectionId");
		String classId = request.getParameter("classId");
		String courseId = request.getParameter("courseId");
		NoteSummary noteSummary = new NoteSummary();
		noteSummary.setClassId(classId);
		noteSummary.setSectionId(sectionId);
		noteSummary.setNote(note);
		noteSummary.setUserId(userId);
		noteSummary.setCourseId(courseId);
		//判断该章节是否已记录课后笔记
		boolean flag = courseService.isExsitNote(userId, sectionId, classId);
		try {
			if(flag){
				//修改该章节课后笔记
				courseService.updateNote(noteSummary);
			}else{
				//保存新增课后笔记
				courseService.insertNote(noteSummary);
			}
			bean.addSuccess();
		} catch (Exception e) {
			logger.error("--------保存异常-------", e);
			bean.addError("保存失败,系统异常");
		}
		return bean;
	}
	
	/**
	* 保存课后总结
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年7月30日 下午5:35:12
	*/
	@RequestMapping("/saveSummary")
	@ResponseBody
	public ResponseBean saveSummary(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		String summary = request.getParameter("summary");
		String sectionId = request.getParameter("sectionId");
		String classId = request.getParameter("classId");
		String courseId = request.getParameter("courseId");
		
		NoteSummary noteSummary = new NoteSummary();
		noteSummary.setClassId(classId);
		noteSummary.setSectionId(sectionId);
		noteSummary.setSummary(summary);
		noteSummary.setUserId(userId);
		noteSummary.setCourseId(courseId);
		//判断该章节是否已记录课后总结
		boolean flag = courseService.isExsitSummary(userId, sectionId,classId);
		try {
			if(flag){
				//修改该章节课后总结
				courseService.modifySummary(noteSummary);
			}else{
				//保存新增课后总结
				courseService.addSummary(noteSummary);
			}
			//修改课程状态
			userCourseRelaService.updateIsFinish(courseId, userId, sectionId, 2,classId);
			bean.addSuccess();
		} catch (Exception e) {
			logger.error("--------保存异常-------", e);
			bean.addError("保存失败,系统异常");
		}
		return bean;
	}
	
	/**
	* 上传新资料
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月1日 下午1:32:49
	*/
	@RequestMapping("/upNewSection")
	public ResponseBean upNewSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		//获取登录的用户信息
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		Section section = new Section();
		section.setSectionId(request.getParameter("sectionId"));
		section.setSectionName(request.getParameter("sectionName"));
		section.setCourseId(request.getParameter("courseId"));
		section.setSectionType(request.getParameter("sectionType"));
		section.setSectionUrl(request.getParameter("sectionUrl"));
		section.setUpdateUser(userId);
		section.setUpdateFileName(request.getParameter("updateFileName"));
		section.setUpLoadId(request.getParameter("upLoadId"));
		section.setUploadPath(request.getParameter("fileMappingPath"));
		try {
			int result = sectionService.addTeachUpSection(section);
			if(result == 1){
				bean.addSuccess();
			}else{
				bean.addDefaultError();
			}
		} catch (Exception e) {
			logger.error("-------保存上传文档失败------", e);
			bean.addDefaultError();
		}
		return bean;
	}
	
	/**
	* 下载个人课件
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年8月2日 上午10:53:29
	*/
	@RequestMapping("/downSelfSection")
	@ResponseBody
	public ResponseBean downSelfSection(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		//获取用户登录的信息
		//String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		String upLoadId = request.getParameter("upLoadId");
		Section section = sectionService.getUpLoadSection(upLoadId);
		
		try {
			String fileRealPath = filePath + section.getSectionUrl().replace("/", "\\")+"\\" + section.getUpLoadId()+"_" + section.getUpdateFileName() + section.getSectionType();
			Map<String,Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, fileRealPath, section.getSectionType(), section.getUpdateFileName());
			if(resultMap != null && "0".equals(resultMap.get("code"))){
				bean.addSuccess(); 
			}else{
				bean.addError(resultMap.get("message").toString());
			}
		} catch (IOException e) {
			logger.error("--------文件下载异常-------", e);
			bean.addError("系统异常");
		}
		return bean;
	}
	 
	
	private static String getDoubleDigit(int num,int totleNum){
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format((float)num/totleNum);
    }
	
	private static List<TrainSectionVo> groupByTotle(List<Map<String,Object>> list){
		List<TrainSectionVo> sectionList = new ArrayList<TrainSectionVo>();
		//按大章节目录进行分组
		Map<String,List<Map<String,Object>>> map = new HashMap<String,List<Map<String,Object>>>();
		for(Map smap : list){
			List<Map<String,Object>> tmpList = map.get(smap.get("totleSectionSort"));
			if(tmpList == null){
				tmpList = new ArrayList<Map<String,Object>>();
				tmpList.add(smap);
				map.put(String.valueOf(smap.get("totleSectionSort")), tmpList);
			}else{
				tmpList.add(smap);
			}
		}
		Set set = map.entrySet();
		Iterator<Set> iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, List<Map<String,Object>>> entry = (Entry<String, List<Map<String,Object>>>) iterator.next();
			TrainSectionVo sectionVo = new TrainSectionVo();
			sectionVo.setTrainSectionSort(entry.getKey());
			sectionVo.setTrainSectionName(String.valueOf(entry.getValue().get(0).get("totleSectionName")));
			sectionVo.setSectionStatusList(entry.getValue());
			sectionList.add(sectionVo);
		}
		return sectionList;
	}
	
}