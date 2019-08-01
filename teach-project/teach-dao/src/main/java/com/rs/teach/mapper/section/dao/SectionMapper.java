package com.rs.teach.mapper.section.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rs.teach.mapper.section.entity.Section;

/**
* SectionMapper.java
* @Description:章节接口
* @author: suzhao
* @date: 2019年7月31日 下午1:26:07
* @version: V1.0
*/
public interface SectionMapper{
	/**
	* 查询用户修改过的课件信息
	* @param 
	* @throws
	* @return Section
	* @author suzhao
	* @date 2019年7月31日 下午2:17:25
	*/
	public List<Section> querySectionByUser(@Param("userId") String userId, @Param("sectionId") String sectionId);
	
	/**
	* 根据sectionId查询章节信息
	* @param sectionId
	* @throws
	* @return Section
	* @author suzhao
	* @date 2019年8月1日 下午1:45:52
	*/
	public Section querySection(String sectionId);
}