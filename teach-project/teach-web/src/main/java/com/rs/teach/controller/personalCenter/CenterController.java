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
* @Description:��������controller
* @author: suzhao
* @date: 2019��7��24�� ����3:06:17
* @version: V1.0
*/
@Controller
@RequestMapping(value="/personalCenter")
public class CenterController{
	/**
	 * �û� service
	 * */
	@Autowired
	private UserService userService;
	
	/**
	 * �α� service
	 * */
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * �༶
	 * */
	@Autowired
	private StudyTeamService studyTeamService;
	
	/**
	 * �γ���Դ service
	 * */
	@Autowired
	private CourseService courseService;
	
	
	
	/**
	* �������Ŀα�
	* @param request
	* @param response
	* @throws
	* @return bean
	* @author suzhao
	* @date 2019��7��24�� ����3:13:02
	*/
	@RequestMapping("/scheduleIndex")
	@ResponseBody
	public ResponseBean scheduleIndex(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		String userId = userInfo.get("userId").toString();	
		//�ж��û��Ƿ�༭����������
		boolean flag = userService.isModifyInfo(userId);
		if(!flag){
			bean.addError("���ȱ༭��������");
			return bean;
		}
		//��ѯ�γ̱�
		List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
		
		bean.addSuccess(schedules);
		return bean;
	}
	
	/**
	* ��ʼ����ʦ��Ϣ���α���Ϣ
	* @param request
	* @param response
	* @throws
	* @return
	* @author suzhao
	* @date 2019��7��25�� ����2:52:30
	*/
	@RequestMapping("/initEditHome")
	@ResponseBody
	public ResponseBean initEditHome(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		String userId = userInfo.get("userId").toString();	
		//��ѯ�û�����У���İ༶
		List<StudyTeam> teams = studyTeamService.getClassById(userId);
		ajaxData.put("teams", teams);
		
		//��ѯ�û����̿γ�
		List<Course> courses = courseService.getCourseByUserId(userId);
		ajaxData.put("courses", courses);
		
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* ����༭��Ŀα�
	* @param request
	* @param response
	* @throws
	* @return bean
	* @author suzhao
	* @date 2019��7��24�� ����5:13:23
	*/
	@RequestMapping("/editSchedule")
	@ResponseBody
	public ResponseBean editSchedule(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		//��ȡ�û����±༭����Ϣ
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