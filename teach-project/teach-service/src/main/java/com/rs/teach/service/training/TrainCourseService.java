package com.rs.teach.service.training;

import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 12:53
 */
public interface TrainCourseService {


    List<TrainCourseVo> selectTrainCourse();

}
