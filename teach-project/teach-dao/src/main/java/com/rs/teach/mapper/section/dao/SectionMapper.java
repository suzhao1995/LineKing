package com.rs.teach.mapper.section.dao;

import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.dto.TotleSectionDto;
import com.rs.teach.mapper.section.vo.TrainLitterSectionVo;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
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
	
	/**
	* 插入老师上传的课件信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 上午10:38:33
	*/
	public int insertTeachUpSection(Section section);
	
	/**
	* 插入老师上传的课件信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 上午10:38:33
	*/
	public Section queryUpLoadSection(String upLoadId);
	
	/**
	* 根据courseId 查询详细章节信息
	* @param courseId
	* @throws
	* @return List<Section>
	* @author suzhao
	* @date 2019年8月12日 下午12:59:52
	*/
	public List<Section> querySectionByCourseId(String courseId);

	/**
	 * 根据课程ID获取当前课程的章节总数
	 * @param courseId
	 * @return
	 */
	String selectSectionNum(String courseId);
	/**
	* 查看章节学习状态
	* @param
	* @throws
	* @return List<Map<String,Object>>
	* @author suzhao
	* @date 2019年8月14日 下午1:10:49
	*/
	public List<Map<String,Object>> querySectionStatus(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId);

	/**
	 * 新增章节
	 * @param sectionDto
	 */
    void addSection(SectionDto sectionDto);

	/**
	 * 根据id查询大章节信息
	 * @param courseId
	 * @return
	 */
	List<TrainSectionVo> selectSectionById(@Param("courseId") String courseId);

	/**
	 * 查询小章节信息
	 * @param courseId
	 * @param trainSectionSortid
	 * @return
	 */
	List<TrainLitterSectionVo> selectLitterSection(@Param("courseId") String courseId, @Param("trainSectionSortid") String trainSectionSortid);

	/**
	 * 修改课程章节信息
	 * @param sectionDto
	 */
	void updateSection(SectionDto sectionDto);
	/**
	 * 查询当前课程大章节序号最大的一条数据
	 * @param courseId
	 * @return
	 */
    TotleSection selectTotoleSectionSortMax(@Param("courseId") String courseId);

	/**
	 * 批量插入
	 * @param list
	 */
	void addAllTotleSection(@Param("list") List<TotleSection> list);

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
	/**
	 * 查询当前课程是否含有大章节
	 * @param courseId
	 * @return
	 */
	Integer IsEmpty(@Param("courseId") String courseId);

	/**
	 * 查询当前课程大章节内有无小章节
	 * @param sectionDto
	 * @return
	 */
	Integer IsBlank(SectionDto sectionDto);

	/**
	 * 查询当前课程大章节中小章节序号最大的一条数据
	 * @param sectionDto
	 * @return
	 */
	TrainLitterSectionVo selectTrainLitterSectionSortMax(SectionDto sectionDto);
}