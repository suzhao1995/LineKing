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
}