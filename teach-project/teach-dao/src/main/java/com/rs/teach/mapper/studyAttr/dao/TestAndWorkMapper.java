package com.rs.teach.mapper.studyAttr.dao;

import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import org.apache.ibatis.annotations.Param;

/**
* TestAndWorkMapper.java
* @Description:练习试卷接口
* @author: suzhao
* @date: 2019年8月12日 下午5:24:11
* @version: V1.0
*/
public interface TestAndWorkMapper{
	/**
	* 获取练习
	* @param workId 练习id
	* @throws
	* @return Practice
	* @author suzhao
	* @date 2019年8月12日 下午5:20:36
	*/
	public Practice queryPracticeById(String workId);
	
	/**
	* 获取试卷
	* @param testId 试卷id
	* @throws
	* @return Testpaper
	* @author suzhao
	* @date 2019年8月12日 下午5:21:23
	*/
	public Testpaper queryTestpaper(String testId);

	/**
	 * 添加练习表
	 * @param practice
	 */
	void insertPractice(Practice practice);
	/**
	 * 添加考试表
	 * @param testpaper
	 */
	void insertTestpaper(Testpaper testpaper);

	/**
	 * 修改练习表
	 * @param practice
	 */
    void updatePractice(Practice practice);

	/**
	 * 修改试卷表
	 * @param testpaper
	 */
	void updateTestpaper(Testpaper testpaper);

	/**
	 * 获取未修改之前的url
	 * @param pid
	 * @return
	 */
	String queryUrlByPid(@Param("pid") String pid);

	/**
	 * 获取未修改之前的url
	 * @param tid
	 * @return
	 */
	String queryUrlByTid(@Param("tid") String tid);

    Practice selectPractice(@Param("sectionId") String sectionId);

	Testpaper selectTestpaper(@Param("sectionId") String sectionId);
	
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