package com.rs.teach.mapper.studyAttr.dao;

import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 13:18
 */
public interface TrainCourseMapper {

    List<TrainCourseVo> selectTrainCourse();

    int count();
}
