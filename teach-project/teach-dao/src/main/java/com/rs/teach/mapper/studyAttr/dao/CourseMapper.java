package com.rs.teach.mapper.studyAttr.dao;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.studyAttr.dto.CourseDto;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import org.apache.ibatis.annotations.Param;

import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.entity.NoteSummary;

public interface CourseMapper{
	/**
	* 查询当前用户所教课程
	* @param userId
	* @throws
	* @return
	* @author suzhao
	* @date 2019年7月29日 上午11:08:27
	*/
	public List<Course> queryCourseById(String userId);
	
	/**
	* 查询用户所教各班级课程
	* @param 
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年7月30日 下午12:46:47
	*/
	public List<Map<String,Object>> courseInfoForUser(@Param("userId") String userId, @Param("classId") String classId);
	
	/**
	* 已学完课程章节数
	* @param userId 用户id
	* @param classId 班级id
	* @param courseId 课程id
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月30日 下午1:10:04
	*/
	public List<Map<String,Object>> finishStudy(@Param("userId") String userId, @Param("classId") String classId, @Param("courseId") String courseId);
	
	/**
	* 查询各班级课程 课后笔记
	* @param 
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年7月30日 下午4:41:16
	*/
	public List<Map<String,Object>> queryNote(Map<String,String> map);
	
	/**
	* 查询各班级课程 课后总结
	* @param 
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年7月30日 下午4:41:16
	*/
	public List<Map<String,Object>> querySummary(Map<String,String> map);
	
	/**
	* 查询该章节是否已做课后总结
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月30日 下午5:45:38
	*/
	public int isExsitSummary(@Param("userId") String userId, @Param("sectionId") String sectionId, @Param("classId") String classId);
	
	/**
	* 修改课后总结
	* @param noteSummary
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月31日 上午11:40:36
	*/
	public int updateSummary(NoteSummary noteSummary);
	
	/**
	* 插入课后总结
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年7月31日 上午11:48:37
	*/
	public int insertSummary(NoteSummary noteSummary);
	
	/**
	* 分页查询所有课程
	* @param 
	* @throws
	* @return List<Course>
	* @author suzhao
	* @date 2019年8月9日 下午12:21:31
	*/
	public List<Course> queryCourse(Map<String,String> conMap);
	
	/**
	* 根据课程id查询课程资源信息
	* @param 
	* @throws
	* @return Course
	* @author suzhao
	* @date 2019年8月12日 上午11:54:36
	*/
	public Course selectCourseByCourseId(String courseId);

	/**
	 * 添加课程
	 * @param courseDto
	 */
    void addCourse(CourseDto courseDto);

	/**
	 * 删除课程
	 * @param courseId
	 */
	void deleteCourse(@Param("courseId") String courseId);

	/**
	 * 修改课程
	 * @param courseDto
	 */
	void updateCourse(CourseDto courseDto);

	/**
	 * 查询所有课程
	 * @return
	 * @param courseDto
	 */
	List<Course> selectCourse(CourseDto courseDto);


	public int isExsitNote(@Param("userId") String userId, @Param("sectionId") String sectionId, @Param("classId") String classId);
	public int updateNote(NoteSummary noteSummary);
	public int insertNote(NoteSummary noteSummary);

	//分组查询课程类型和课程等级
	public List<Map<String,Object>> groupCourseType();
	public List<Map<String,Object>> groupCourseLev();

	//管理员查询全部课程
    List<TrainCourseVo> selectTrainCourse(SectionDto sectionDto);

	/**
	 * 根据id查询大章节信息
	 * @param courseId
	 * @return
	 */
	TrainCourseVo selectCourseById(@Param("courseId") String courseId);
}