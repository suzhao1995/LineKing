package com.rs.teach.controller.personalCenter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.NoteSummary;
import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.timeTable.entity.Schedule;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.section.SectionService;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.studyAttr.StudyTeamService;
import com.rs.teach.service.timeTable.ScheduleService;

/**
* CenterController.java
* @Description:个人中心controller
* @author: suzhao
* @date: 2019年7月24日 下午3:06:17
* @version: V1.0
*/
@Controller
@RequestMapping(value="/personalCenter")
public class CenterController{
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
			bean.addError("请先编辑个人资料");
			return bean;
		}
		//查询课程表
		List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
		
		bean.addSuccess(schedules);
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
		schedule.setTeachUserName(userInfo.get("userName").toString());
		schedule.setWeekDay(Integer.valueOf(request.getParameter("weekDay")));
		schedule.setStartDate(request.getParameter("startDate"));
		schedule.setEndDate(request.getParameter("endDate"));
		schedule.setClassId(request.getParameter("classId"));
		schedule.setCurriculumId(request.getParameter("curriculumId"));
		if("0".equals(flag)){
			//新增
			resultCode = scheduleService.addSchedule(schedule);
		}else{
			resultCode = scheduleService.modifySchedule(schedule);
		}
		if(resultCode == 1){
			bean.addSuccess();
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
		
		//查询用户所教班级
		List<Map<String,Object>> teams = scheduleService.getStudyTeamByUserId(userId);
		ajaxData.put("teams", teams);
		String classId = request.getParameter("classId");
		if("all".equals(classId)){
			classId = null;
		}
		//查询用户所教各班级课程
		List<Map<String,Object>> list = courseService.getCourseInfoForUser(userId,classId);
		for(Map<String,Object> map : list){
			List<Map<String,Object>> finishSec = courseService.getFinishStudy(userId, map.get("classId").toString(), map.get("courseId").toString());
			int number = finishSec == null ? 0 : finishSec.size();	//已学完章节数量
			map.put("finishNumber", number);
			int totleNum = Integer.valueOf(map.get("sectionNumber").toString());	//课程章节总数
			String percentage = getDoubleDigit(number,totleNum);	//进度条 保留两位小数
			map.put("percentage", percentage);	
		}
		ajaxData.put("speedInfo", list);
		bean.addSuccess(ajaxData);
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
	@RequestMapping("/courseInfo")
	@ResponseBody
	public ResponseBean courseInfo(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		String classId = request.getParameter("classId");
		String courseId = request.getParameter("courseId");
		
		List<Map<String,Object>> finishSec = courseService.getFinishStudy(userId, classId, courseId);
		ajaxData.put("finishSec", finishSec);
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 课后笔记，课后总结
	* @param 
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年7月30日 下午3:43:12
	*/
	@RequestMapping("/queryNoteSummary")
	@ResponseBody
	public ResponseBean queryNote(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		//获取登录的用户id
		String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
		
		String classId = request.getParameter("classId");
		String sectionId = request.getParameter("sectionId");
		String courseId = request.getParameter("courseId");
		String code = request.getParameter("code");	//课后笔记：0， 课后总结：1, 我的资源：2
		if("2".equals(code)){
			//查看我修改的课件信息
			List<Section> sections = sectionService.getSectionByUser(userId, sectionId);
			if(sections.size() >= 0){
				bean.addSuccess(sections);
			}else{
				bean.addError("本章节，您未上传过课件");
				return bean;
			}
		}else{
			List<Map<String,Object>> list = courseService.getNoteSummary(userId, classId, courseId, code, sectionId);
			if(list.size() > 0){
				Map<String,Object> resultMap = list.get(0);
				bean.addSuccess(resultMap);
			}
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
			bean.addSuccess();
		} catch (Exception e) {
			bean.addError("保存失败,系统异常");
		}
		return bean;
	}
	 
	
	private static String getDoubleDigit(int num,int totleNum){
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format((float)num/totleNum);
    }
	
	
}