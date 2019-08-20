package com.rs.teach.service.training;

import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;

import java.util.List;

/**
 * @author 汪航
 * @Description 章节service
 * @create 2019-08-03 11:10
 */
public interface TrainSectionService {
    /**
     * 根据课程ID查询章节信息
     * @param courseId
     * @return
     */
    TrainCourseVo selectCourseSection(String courseId);

    /**
     * 查询返回页面信息（1.当前小章节全部信息)
     * @param sectionId
     * @return
     */
    TrainSection selectTrainSection(String sectionId);

    /**
     * 查询小章节目录
     * @param trainCourseId
     * @param trainSectionSort
     * @return
     */
    List<TrainSection> selectSectionList(String trainCourseId, String trainSectionSort);

    /**
     * 添加章节
     * @param sectionDto
     *
     */
    void addTrainSection(SectionDto sectionDto);

    /**
     * 修改章节
     * @param sectionDto
     */
    void updateTrainSection(SectionDto sectionDto);

    /**
     * 通过课程id查询所有章节信息
     * @param courseId
     * @return
     */
    List<Section> getSectionByCourseId(String courseId);
}
