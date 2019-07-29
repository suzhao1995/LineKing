package com.rs.teach.controller.index;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
* @date: 2019��7��23�� ����3:07:26
* @version: V1.0
*/
@Controller
@RequestMapping(value = "/index")
public class IndexController {
	
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
	* ͼ����֤�����ɷ���
	* @param request,response
	* @throws 
	* @return bean
	* @author suzhao
	* @date 2019��7��23�� ����11:33:09
	*/
	@RequestMapping("/verifyCode")
	@ResponseBody
	public ResponseBean verifyCode(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		JSONObject json = new JSONObject();
		
		String randomCode = ImageUtil.generateVerifyCode(4);
		try {
			String verifyCode = ImageUtil.outputImage(165, 66, randomCode);
			verifyCode = "data:image/png;base64,"+verifyCode;
			json.put("verifyCode", verifyCode);
			json.put("randomCode", randomCode);
			bean.addSuccess(json);
		} catch (IOException e) {
			bean.addError(ResponseBean.CODE_SYS_ERROR, "ϵͳ�쳣");
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	* �û���¼
	* @param request,response
	* @throws
	* @return ResponseBean
	* @author suzhao
	* @date 2019��7��22�� ����4:51:42
	*/
	@RequestMapping("/login")
	@ResponseBody
	public ResponseBean userLogin(HttpServletRequest request, HttpServletResponse response){
		ResponseBean bean = new ResponseBean();
		JSONObject json = new JSONObject();
		
		//��ȡ�û�������Ϣ
		String userId = request.getParameter("userId");
		String passWord = request.getParameter("passWord");
		
		try {
			User loginUser = userService.getUserById(userId);
			if(loginUser == null){
				bean.addError(ResponseBean.CODE_SYS_ERROR, "�˺Ų����ڣ���˶�");
			}else if(!passWord.equals(loginUser.getPassWord())){
				bean.addError(ResponseBean.CODE_SYS_ERROR, "�����������������");
			}else if(passWord.equals(loginUser.getPassWord())){
				//��¼�ɹ��������û���Ϣ��session
				String sessionInfo = loginUser.getUserId() +","+loginUser.getUserName();
				SessionUtil.cleanOldSession(sessionInfo);	//����û��ٴε�¼����Ѵ��ڵ�session��ȷ��ÿ���û�ֻ��һ��session
				HttpSession session = request.getSession(true);
				session.setAttribute("userInfo", sessionInfo);
				session.setMaxInactiveInterval(-1);	//����session����ʱ��
				String sessionKey = System.currentTimeMillis()+"";
				SessionUtil.getSessionMap().put(sessionKey, session);
				loginUser.setSessionKey(sessionKey);
				json.put("userInfo", loginUser);
				bean.addSuccess(json);
			}
			
		} catch (Exception e) {
			System.out.println("======�û���¼�쳣====="+e);
			bean.addError(ResponseBean.CODE_SYS_ERROR, "ϵͳ�쳣");
		}
		return bean;
	}
	
	
	/**
	* ��ҳչʾ
	* @param request,response
	* @throws
	* @return bean
	* @author suzhao
	* @throws Exception 
	* @date 2019��7��23�� ����3:07:38
	*/
	@RequestMapping("/home")
	@ResponseBody
	public ResponseBean indexHome(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseBean bean = new ResponseBean();
		Map<String,Object> ajaxData = new HashMap<String,Object>();
		
		//��ȡ�û���Ϣ
		Map<String,Object> userInfo = UserInfoUtil.getUserInfo(request.getParameter("sessionKey"));
		ajaxData.put("userName", userInfo.get("userName"));
		
		if(userInfo != null){
			String userId = userInfo.get("userId").toString();
			List<Schedule> schedules = scheduleService.getSchedulesByUserId(userId);
			Schedule schedule = getSchedule(schedules);
			if(schedule != null){
				ajaxData.put("isContainsTable", "0");
				ajaxData.put("curriculum", schedule.getCurriculum());	//�γ���
				ajaxData.put("startDate", schedule.getStartDate());	//�γ̿�ʼ
				ajaxData.put("endDate", schedule.getEndDate());	//�γ̽���
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
	* ��ȡ��һ���Ͽ�ʱ��
	* @param schedules �α�list
	* @throws Exception
	* @return Schedule �α�
	* @author suzhao
	* @date 2019��7��24�� ����1:31:48
	*/
	public Schedule getSchedule(List<Schedule> schedules) throws Exception{
		Schedule nextSchedule = null;
		//��ȡ�û���ǰ��¼ʱ��
		int weekDay = DateUtil.getWeekDay(new Date());
		long loginTime = new Date().getTime();	//
		if(schedules.size() > 0){
			for(int i = 0; i < schedules.size(); i++){
				if(weekDay <= 5){
					if(weekDay == schedules.get(i).getWeekDay() && DateUtil.StringToDate(schedules.get(i).getStartDate(), "hh:mm").getTime() > loginTime){
						return schedules.get(i);
					}else{
						if(i < schedules.size()){
							//ȡ��һ�ο�
							return schedules.get(i+1);
						}else{
							//ȡÿ�ܵ�һ�ڿ�
							return schedules.get(0);
						}
					}
				}else if(weekDay > 5){
					//ȡÿ�ܵ�һ�ڿ�
					return schedules.get(0);
				}
			}
		}
		return nextSchedule;
	}

}
