package com.rs.teach.service.studyAttr;

import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;

/**
* TestAndWorkService.java
* @Description:练习和试卷service
* @author: suzhao
* @date: 2019年8月12日 下午5:13:10
* @version: V1.0
*/
public interface TestAndWorkService{
	/**
	* 获取练习
	* @param 
	* @throws
	* @return Practice
	* @author suzhao
	* @date 2019年8月12日 下午5:20:36
	*/
	public Practice getPracticeById(String workId);
	
	/**
	* 获取试卷
	* @param 
	* @throws
	* @return Testpaper
	* @author suzhao
	* @date 2019年8月12日 下午5:21:23
	*/
	public Testpaper getTestpaper(String testId);
}