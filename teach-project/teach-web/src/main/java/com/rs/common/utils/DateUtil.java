package com.rs.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* DateUtil.java
* @Description:���ڹ�����
* @author: suzhao
* @date: 2019��7��23�� ����5:59:49
* @version: V1.0
*/
public class DateUtil{
	
	/**
	* ����ת��Ϊ�ܼ�
	* @param date
	* @throws
	* @return String
	* @author suzhao
	* @date 2019��7��23�� ����6:15:38
	*/
	public static int getWeekDay(Date date){
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		//weekday=1������������;weekday=2����������һ;...;weekday=7��������������
		if(weekDay == 1){
			return 7;
		}
		return weekDay - 1;
	}
	
	/**
	* ����ת����ʽ
	* @param date
	* @param format ���ڸ�ʽ��yyyy-MM-dd
	* @throws
	* @return
	* @author suzhao
	* @date 2019��7��23�� ����6:26:11
	*/
	public static String dateFormat(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	
	/**
	* �ַ���ת������  ��ʽ��Ӧ
	* @param day 8:00
	* @param format ��ʽ hh��mm
	* @throws ParseException
	* @return date
	* @author suzhao
	* @date 2019��7��24�� ����11:55:44
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