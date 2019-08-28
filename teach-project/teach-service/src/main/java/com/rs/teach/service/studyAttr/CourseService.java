package com.rs.teach.service.studyAttr;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.NoteSummary;
import com.rs.teach.mapper.studyAttr.dto.CourseDto;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;

/**
* CourseService.java
* @Description:课程资源service
* @author: suzhao
* @date: 2019年7月29日 上午11:15:57
* @version: V1.0
*/
public interface CourseService{
	/**
	* 查询当前用户所教课程
	* @param userId
	* @throws
	* @return
	* @author suzhao
	* @date 2019年7月29日 上午11:16:41
	*/
	 List<Course> getCourseByUserId(String userId);
	
	/**
	* 查询用户所教各班级课程
	* @param userId
	* @param classId 班级Id
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年7月30日 下午12:53:03
	*/
	 List<Map<String,Object>> getCourseInfoForUser(String userId, String classId);
	
	/**
	* 已学完课程章节数
	* @param userId 用户id
	* @param classId 班级id
	* @param courseId 课程id
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年7月30日 下午1:10:04
	*/
	 List<Map<String,Object>> getFinishStudy(String userId, String classId, String courseId);
	
	/**
	* 查询各班级课程 课后笔记
	* @param 
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年7月30日 下午4:41:16
	*/
	 String getNoteSummary(String userId, String classId, String courseId, String code, String sectionId);
	
	/**
	* 查询该章节是否已做课后总结
	* @param 
	* @throws
	* @return boolean
	* @author suzhao
	* @date 2019年7月30日 下午5:45:38
	*/
	 boolean isExsitSummary(String userId, String sectionId,String classId);
	
	/**
	* 修改课后总结
	* @param noteSummary
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月31日 上午11:40:36
	*/
	int modifySummary(NoteSummary noteSummary);
	
	/**
	* 插入课后总结
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月31日 上午11:48:37
	*/
	int addSummary(NoteSummary noteSummary);
	
	/**
	* 课程资源初始化页面接口
	* @param 
	* @throws
	* @return List<Course>
	* @author suzhao
	* @date 2019年8月9日 下午4:23:15
	*/
	 List<Course> getCourse(String courseType, String schoolId, String courseLev,String likeSearch);
	
	/**
	* 根据课程id查询课程资源信息
	* @param 
	* @throws
	* @return Course
	* @author suzhao
	* @date 2019年8月12日 上午11:54:36
	*/
	Course queryCourseByCourseId(String courseId);

	/**
	 * 添加课程
	 * @param courseDto
	 */
    void addCourse(CourseDto courseDto);

	/**
	 * 删除课程
	 * @param courseId
	 */
	void deleteCourse(String courseId);

	/**
	 * 修改课程
	 * @param courseDto
	 */
	void updateCourse(CourseDto courseDto);

	/**
	 * 查询全部课程
	 */
	List<Course> selectCourse(CourseDto courseDto);

	public boolean isExsitNote(String userId, String sectionId, String classId);
	public int updateNote(NoteSummary noteSummary);
	public int insertNote(NoteSummary noteSummary);

	//分组查询课程类型和课程等级
	public List<Map<String,Object>> groupCourseType();
	public List<Map<String,Object>> groupCourseLev();

	//管理员查询全部课程
    List<TrainCourseVo> selectTrainCourse(SectionDto sectionDto);

}