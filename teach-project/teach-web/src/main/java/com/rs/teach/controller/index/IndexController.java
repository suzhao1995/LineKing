package com.rs.teach.controller.index;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.rs.common.utils.DateUtil;
import com.rs.common.utils.ImageUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.SessionUtil;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.timeTable.entity.Schedule;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.timeTable.ScheduleService;

/**
* IndexController.java
* @Description:TODO
* @author: suzhao
* @date: 2019年7月23日 下午3:07:26
* @version: V1.0
*/
@Controller
@RequestMapping(value = "/index")
public class IndexController {
	private Logger logger  = Logger.getLogger(IndexController.class);
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
	* 图形验证码生成方法
	* @param request,response
	* @throws 
	* @return bean
	* @author suzhao
	* @date 2019年7月23日 上午11:33:09
	*/
	@RequestMapping("/verifyCode")
	@ResponseBody
	public ResponseBean verifyCode(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		JSONObject json = new JSONObject();
		
		String randomCode = ImageUtil.generateVerifyCode(4);
		logger.info(randomCode);
		try {
			String verifyCode = ImageUtil.outputImage(165, 66, randomCode);
			verifyCode = "data:image/png;base64,"+verifyCode;
			json.put("verifyCode", verifyCode);
			json.put("randomCode", randomCode);
			bean.addSuccess(json);
		} catch (IOException e) {
			bean.addError(ResponseBean.CODE_SYS_ERROR, "系统异常");
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	* 用户登录
	* @param request,response
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019年7月22日 下午4:51:42
	*/
	@RequestMapping("/login")
	@ResponseBody
	public ResponseBean userLogin(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		JSONObject json = new JSONObject();
		
		//获取用户输入信息
		String userId = request.getParameter("userId");
		String passWord = request.getParameter("passWord");
		
		try {
			User loginUser = userService.getUserById(userId);
			if(loginUser == null){
				bean.addError(ResponseBean.CODE_SYS_ERROR, "账号不存在，请核对");
			}else if(!passWord.equals(loginUser.getPassWord())){
				bean.addError(ResponseBean.CODE_SYS_ERROR, "密码错误，请重新输入");
			}else if(passWord.equals(loginUser.getPassWord())){
				//登录成功，保存用户信息到session
				String sessionInfo = loginUser.getUserId() +","+loginUser.getUserName();
				SessionUtil.cleanOldSession(sessionInfo);	//如果用户再次登录清除已存在的session，确保每个用户只有一个session
				HttpSession session = request.getSession(true);
				session.setAttribute("userInfo", sessionInfo);
				session.setMaxInactiveInterval(-1);	//设置session过期时间
				String sessionKey = System.currentTimeMillis()+"";
				SessionUtil.getSessionMap().put(sessionKey, session);
				loginUser.setSessionKey(sessionKey);
				json.put("userInfo", loginUser);
				bean.addSuccess(json);
			}
			
		} catch (Exception e) {
			System.out.println("=======用户登录异常====="+e);
			bean.addError(ResponseBean.CODE_SYS_ERROR, "系统异常");
		}
		return bean;
	}
	
	
	/**
	* 首页展示
	* @param request,response
	* @throws
	* @return bean
	* @author suzhao
	* @throws Exception 
	* @date 2019年7月23日 下午3:07:38
	*/
	@RequestMapping("/home")
	@ResponseBody
	public ResponseBean indexHome(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		//获取用户信息
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		ajaxData.put("userName", userInfo.get("userName"));
		
		if(userInfo != null){
			String userId = userInfo.get("userId").toString();
			List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
			Schedule schedule = getSchedule(schedules);
			if(schedule != null){
				ajaxData.put("isContainsTable", "0");//是否编辑过课表
				ajaxData.put("curriculum", schedule.getCurriculum());	//课程名
				ajaxData.put("startDate", schedule.getStartDate());	//课程开始
				ajaxData.put("endDate", schedule.getEndDate());	//课程结束
			}else{
				ajaxData.put("isContainsTable", "-1");
			}
		}else{
			ajaxData.put("isContainsTable", "-1");
		}
		bean.addSuccess(ajaxData);
		return bean;
	}
	
	/**
	* 获取下一次上课时间
	* @param schedules 课表list
	* @throws Exception
	* @return Schedule 课表
	* @author suzhao
	* @date 2019年7月24日 下午1:31:48
	*/
	public Schedule getSchedule(List<Schedule> schedules) throws Exception{
		Schedule nextSchedule = null;
		//获取用户当前登录时间
		int weekDay = DateUtil.getWeekDay(new Date());
		long loginTime = new Date().getTime();	//
		if(schedules.size() > 0){
			for(int i = 0; i < schedules.size(); i++){
				if(weekDay <= 5){
					if(weekDay == schedules.get(i).getWeekDay() && DateUtil.StringToDate(schedules.get(i).getStartDate(), "hh:mm").getTime() > loginTime){
						return schedules.get(i);
					}else{
						if(i < schedules.size()){
							//取下一次课
							return schedules.get(i+1);
						}else{
							//取每周第一节课
							return schedules.get(0);
						}
					}
				}else if(weekDay > 5){
					//取每周第一节课
					return schedules.get(0);
				}
			}
		}
		return nextSchedule;
	}

}
