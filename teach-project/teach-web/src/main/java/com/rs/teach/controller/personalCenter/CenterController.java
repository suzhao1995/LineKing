package com.rs.teach.controller.personalCenter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.Pdf2ImageUtil;
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
		schedule.setCurriculumId(request.getParameter("courseId"));
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
		List<Map<String,Object>> teams = scheduleService.getStudyTeamByUserId(userId);
		ajaxData.put("teams", teams);
		String classId = request.getParameter("classId");
		if("all".equals(classId)){
			classId = null;
		}
		
		//初始化分页信息
		PageHelper.startPage(Integer.valueOf(pageNum), 9);
		//查询用户所教各班级课程
		List<Map<String,Object>> list = courseService.getCourseInfoForUser(userId,classId);
		for(Map<String,Object> map : list){
			List<Map<String,Object>> finishSec = courseService.getFinishStudy(userId, map.get("classId").toString(), map.get("courseId").toString());
			int number = finishSec == null ? 0 : finishSec.size();	//已学完章节数量
			map.put("finishNumber", number);
			int totleNum = Integer.valueOf(map.get("sectionNumber").toString());	//课程章节总数
			double doubleDigit = Double.valueOf(getDoubleDigit(number,totleNum));	//进度条
			int percentage = (int)(doubleDigit * 100);	
			
			map.put("percentage", percentage);	
		}
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list,9);
		ajaxData.put("speedInfo", pageInfo);
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 初始化我的课程信息
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
	 * @throws IOException 
	* @date 2019年7月30日 下午3:43:12
	*/
	@RequestMapping("/queryNoteSummary")
	@ResponseBody
	public ResponseBean queryNote(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		
		Map<String,Object> ajaxData = new HashMap<String,Object>();
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
				ajaxData.putAll(resultMap);
			}
			if("0".equals(code)){
				//转换章节信息为图片流 传给前端
				Section section = sectionService.getSectionById(sectionId);
				String fileName = section.getSectionId()+"_"+section.getUpdateFileName();
				Map<String, Object> returnMap = null;
				try {
					returnMap = Pdf2ImageUtil.pdf2png(request, section.getSectionUrl(), fileName, "png");
					if("0".equals(returnMap.get("code"))){
						ajaxData.put("imgList", returnMap.get("imgList"));
						ajaxData.put("pageCount", returnMap.get("pageCount"));
						bean.addSuccess(ajaxData);
					}else{
						bean.addError(returnMap.get("message").toString());
					}
				} catch (Exception e) {
					logger.error("------"+fileName+"文件转换异常------", e);
					bean.addError("系统异常");
				}
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
			logger.error("--------保存异常-------", e);
			bean.addError("保存失败,系统异常");
		}
		return bean;
	}
	
	/**
	* 个人中心-上传新资料
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
	* 个人中心-下载个人课件
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
			Map<String,Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, section.getUpLoadId(), section.getUpdateFileName(), section.getSectionUrl(), section.getSectionType(), section.getUpdateFileName());
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
	
}