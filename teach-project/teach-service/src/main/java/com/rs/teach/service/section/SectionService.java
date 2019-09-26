package com.rs.teach.service.section;

import java.util.List;
import java.util.Map;


import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.dto.TotleSectionDto;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;

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
	 List<Section> getSectionByUser(String userId, String sectionId);
	
	/**
	* 根据sectionId查询章节信息
	* @param sectionId
	* @throws
	* @return Section
	* @author suzhao
	* @date 2019年8月1日 下午1:45:52
	*/
	 Section getSectionById(String sectionId);
	
	/**
	* 插入老师上传的课件信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 上午10:38:33
	*/
	 int addTeachUpSection(Section section);
	
	/**
	* 根据upLoadId查询教师上传的文档信息
	* @param upLoadId
	* @throws
	* @return Section
	* @author suzhao
	* @date 2019年8月2日 上午11:41:15
	*/
	 Section getUpLoadSection(String upLoadId);
	
	/**
	* 根据courseId 查询详细章节信息
	* @param courseId
	* @throws
	* @return List<Section>
	* @author suzhao
	* @date 2019年8月12日 下午12:59:52
	*/
	 List<Section> getSectionByCourseId(String courseId);
	
	/**
	* 查看章节学习状态
	* @param 
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年8月14日 下午1:10:49
	*/
	 List<Map<String,Object>> getSectionStatus(String courseId, String userId, String classId);

	/**
	 * 添加章节
	 * @param sectionDto
	 *
	 */
    void addSection(SectionDto sectionDto);
	/**
	 * 查询课程章节信息
	 * @param courseId
	 * @return
	 */
	TrainCourseVo selectCourseSection(String courseId);

	/**
	 * 修改课程章节信息
	 * @param sectionDto
	 */
	void updateSection(SectionDto sectionDto);

	/**
	 * 大章节添加
	 * @param totleSectionDto
	 */
    void addTotleSection(TotleSectionDto totleSectionDto);

	/**
	 * 查询大章节
	 * @param totleSectionDto
	 * @return
	 */
	List<TotleSection> selectTotleSection(TotleSectionDto totleSectionDto);

	/**
	 * 修改大章节名
	 * @param totleSectionDto
	 */
	void updateTotleSection(TotleSectionDto totleSectionDto);

    SectionDto selectPidAndTid(String sectionId);

}