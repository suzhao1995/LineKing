package com.rs.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* DateUtil.java
* @Description:日期工具类
* @author: suzhao
* @date: 2019年7月23日 下午5:59:49
* @version: V1.0
*/
public class DateUtil{
	
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
	
	public static void main(String[] args) throws ParseException {
		Date d = StringToDate("8:00","hh:mm");
		Date date = new Date();
		System.out.println(date.getTime() > d.getTime());
	}
}