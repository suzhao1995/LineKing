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

	/**
	 * 获取未修改之前的url
	 * @param pid
	 * @return
	 */
	String queryUrlByPid(String pid);

	/**
	 * 获取未修改之前的url
	 * @param tid
	 * @return
	 */
	String queryUrlByTid(String tid);

	/**
	 * 查询作业文件
	 * @param sectionId
	 * @return
	 */
    Practice selectPractice(String sectionId);

	/**
	 * 查询试卷文件
	 * @param sectionId
	 * @return
	 */
	Testpaper selectTestpaper(String sectionId);
	
	/**
	* 删除试卷
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月4日 下午3:36:00
	*/
	void delTestPaper(String tid);
	
	/**
	* 删除试卷
	* @param 
	* @throws
	* @return void
	* @author suzhao
	* @date 2019年9月4日 下午3:36:00
	*/
	void delWork(String pid);
}