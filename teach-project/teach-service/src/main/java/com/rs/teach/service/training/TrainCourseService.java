package com.rs.teach.service.training;

import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.mapper.studyAttr.dto.CourseDto;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;

import java.util.List;

/**
 * @author 汪航
 * @Description 培训课程service
 * @create 2019-08-02 12:53
 */
public interface TrainCourseService {

    /**
     * 查询培训课程
     * @return
     * @param courseDto
     */
    List<TrainCourseVo> selectTrainCourse(CourseDto courseDto);

    /**
     * 添加培训课程
     * @param courseDto
     */
    void addTrainCourse(CourseDto courseDto);

    /**
     * 删除培训课程
     * @param courseId
     */
    void deleteTrainCourse(String courseId);

    /**
     * 修改培训课程
     * @param courseDto
     */
    void updateTrainCourse(CourseDto courseDto);

    /**
     * 通过课程ID查询课程信息
     * @param courseId
     * @return
     */
    TrainCourseVo selectTrainCourseById(String courseId);

    /**
     * 查询所有培训课程科目
     * @return
     */
    List<OptionVo> trainCourseList();
}
