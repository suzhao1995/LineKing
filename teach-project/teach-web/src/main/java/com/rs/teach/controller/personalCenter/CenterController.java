package com.rs.teach.controller.personalCenter;

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
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.StudyTeam;
import com.rs.teach.mapper.timeTable.entity.Schedule;
import com.rs.teach.service.User.UserService;
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
		Schedule schedule = new Schedule();
		schedule.setTeachUserId(userInfo.get("userId").toString());
		schedule.setTeachUserName(userInfo.get("userName").toString());
		schedule.setWeekDay(Integer.valueOf(request.getParameter("weekDay")));
		schedule.setStartDate(request.getParameter("startDate"));
		schedule.setEndDate(request.getParameter("endDate"));
		schedule.setClassName(request.getParameter("classId"));
		schedule.setCurriculum(request.getParameter("curriculumId"));
		
		int resultCode = scheduleService.addSchedule(schedule);
		if(resultCode == 1){
			bean.addSuccess();
		}
		
		return bean;
	}
	
	
}