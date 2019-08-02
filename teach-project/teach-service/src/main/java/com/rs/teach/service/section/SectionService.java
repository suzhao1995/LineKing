package com.rs.teach.service.section;

import java.util.List;

import com.rs.teach.mapper.section.entity.Section;

/**
* SectionService.java
* @Description:课程章节service
* @author: suzhao
* @date: 2019年7月31日 下午2:10:14
* @version: V1.0
*/
public interface SectionService{
	
	/**
	* 查询用户修改过的课件信息
	* @param 
	* @throws
	* @return Section
	* @author suzhao
	* @date 2019年7月31日 下午2:17:25
	*/
	public List<Section> getSectionByUser(String userId, String sectionId);
	
	/**
	* 根据sectionId查询章节信息
	* @param sectionId
	* @throws
	* @return Section
	* @author suzhao
	* @date 2019年8月1日 下午1:45:52
	*/
	public Section getSectionById(String sectionId);
	
	/**
	* 插入老师上传的课件信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 上午10:38:33
	*/
	public int addTeachUpSection(Section section);
	
	/**
	* 根据upLoadId查询教师上传的文档信息
	* @param upLoadId
	* @throws
	* @return Section
	* @author suzhao
	* @date 2019年8月2日 上午11:41:15
	*/
	public Section getUpLoadSection(String upLoadId);
}