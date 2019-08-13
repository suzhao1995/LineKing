package com.rs.teach.mapper.section.dao;

import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.section.vo.TrainLitterSectionVo;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:25
 */
public interface TrainSectionMapper {

    List<TrainCourseVo> selectCourseSection(@Param("courseId")String courseId);

    TrainSection selectTrainSection(@Param("sectionId") String sectionId);

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
}
