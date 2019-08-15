package com.rs.teach.mapper.studyAttr.dao;

import java.util.List;
import java.util.Map;

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
	
	public int isExsitNote(@Param("userId") String userId, @Param("sectionId") String sectionId, @Param("classId") String classId);
	public int updateNote(NoteSummary noteSummary);
	public int insertNote(NoteSummary noteSummary);
	
	//分组查询课程类型和课程等级
	public List<String> groupCourseType();
	public List<String> groupCourseLev();
}