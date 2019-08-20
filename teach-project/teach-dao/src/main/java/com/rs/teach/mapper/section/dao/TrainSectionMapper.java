package com.rs.teach.mapper.section.dao;

import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.section.vo.TrainLitterSectionVo;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:25
 */
public interface TrainSectionMapper {

    /**
     * 查询返回页面信息（1.当前小章节全部信息)
     * @param sectionId
     * @return
     */
    TrainSection selectTrainSection(@Param("sectionId") String sectionId);

    /**
     * 查询小章节目录
     * @param trainCourseId
     * @param trainSectionSort
     * @return
     */
    List<TrainSection> selectSectionList(@Param("trainCourseId") String trainCourseId, @Param("trainSectionSort") String trainSectionSort);

    /**
     * 根据id查询大章节信息
     * @param courseId
     * @return
     */
    List<TrainSectionVo> selectTrainSectionById(@Param("courseId") String courseId);

    /**
     * 查询小章节信息
     * @param courseId
     * @param trainSectionSort
     * @return
     */
    List<TrainLitterSectionVo> selectTrainLitterSection(@Param("courseId") String courseId, @Param("trainSectionSort") String trainSectionSort);

    /**
     * 根据课程ID获取当前课程的章节总数
     * @param courseId
     * @return
     */
    Integer selectSectionNum(@Param("courseId") String courseId);

    /**
     * 添加培训章节
     * @param sectionDto
     *
     */
    void addTrainSection(SectionDto sectionDto);

    /**
     * 修改培训章节
     * @param sectionDto
     */
    void updateTrainSection(SectionDto sectionDto);

    /**
     * 通过课程id查询所有章节信息
     * @param courseId
     * @return
     */
    List<Section> getSectionByCourseId(@Param("courseId") String courseId);
}
