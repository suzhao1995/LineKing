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

	/**
	 * 添加练习表
	 * @param practice
	 */
	void insertPractice(Practice practice);

	/**
	 * 添加试卷表
	 * @param testpaper
	 */
	void insertTestpaper(Testpaper testpaper);

	/**
	 * 修改练习
	 * @param practice
	 */
    void updatePractice(Practice practice);

	/**
	 * 修改试卷
	 * @param testpaper
	 */
	void updateTestpaper(Testpaper testpaper);
}