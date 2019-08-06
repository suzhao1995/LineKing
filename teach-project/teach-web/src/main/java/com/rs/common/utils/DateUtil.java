package com.rs.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
* DateUtil.java
* @Description:日期工具类
* @author: suzhao
* @date: 2019年7月23日 下午5:59:49
* @version: V1.0
*/
public class DateUtil{
	private static final Map<Integer,String> WEEK_MAP = new HashMap<Integer,String>();
	static{
		WEEK_MAP.put(1, "周一");
		WEEK_MAP.put(2, "周二");
		WEEK_MAP.put(3, "周三");
		WEEK_MAP.put(4, "周四");
		WEEK_MAP.put(5, "周五");
		WEEK_MAP.put(6, "周六");
		WEEK_MAP.put(7, "周日");
	}
	/**
	* 日期转换为周几
	* @param date
	* @throws
	* @return String
	* @author suzhao
	* @date 2019年7月23日 下午6:15:38
	*/
	public static int getWeekDay(Date date){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		//weekday=1，当天是周日;weekday=2，当天是周一;...;weekday=7，当天是周六。
		if(weekDay == 1){
			return 7;
		}
		return weekDay - 1;
	}
	
	/**
	* 日期转换格式
	* @param date
	* @param format 日期格式：yyyy-MM-dd
	* @throws
	* @return
	* @author suzhao
	* @date 2019年7月23日 下午6:26:11
	*/
	public static String dateFormat(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/**
	* 字符串转换日期  格式对应
	* @param day 8:00
	* @param format 格式 hh：mm
	* @throws ParseException
	* @return date
	* @author suzhao
	* @date 2019年7月24日 上午11:55:44
	*/
	public static Date StringToDate(String day, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(day);
	}
	
	/**
	* 根据WEEK_MAP值获取具体周几信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月6日 上午11:44:42
	*/
	public static String getWeek(int weekDay){
		return WEEK_MAP.get(weekDay);
	}
	
	/**
	* 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	* 
	* @param nowTime 当前时间
	* @param startTime 开始时间
	* @param endTime 结束时间
	* @return
	* @author jqlin
	*/
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}
	
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
	
		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);
	
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
	
		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	* 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	* 
	* @param nowTime 当前时间
	* @param startTime 开始时间
	* @param endTime 结束时间
	* @return
	* @author jqlin
	*/
	public static String sayHello(Date nowTime) {
		int now = Integer.valueOf(dateFormat(nowTime,"hhmmss"));
		if(now<=55959 && now > 0){
			return "凌晨好";
		}else if(now > 55959 && now <= 115959){
			return "早上好";
		}else if(now > 115959 && now <= 135959){
			return "中午好";
		}else if(now > 135959 && now <= 175959){
			return "下午好";
		}else if(now > 175959 && now <= 235959){
			return "晚上好";
		}
		return "中午好";
	}
	
	public static void main(String[] args) throws ParseException {
		int now = Integer.valueOf(dateFormat(new Date(),"hhmmss"));
		System.out.println(now);
	}
}