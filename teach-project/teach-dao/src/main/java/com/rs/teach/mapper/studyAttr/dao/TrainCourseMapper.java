package com.rs.teach.mapper.studyAttr.dao;

import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.mapper.studyAttr.dto.CourseDto;
import com.rs.teach.mapper.studyAttr.vo.CourseAllUrl;
import com.rs.teach.mapper.studyAttr.vo.CourseVo;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 13:18
 */
public interface TrainCourseMapper {

    /**
     * 查询培训课程
     * @return
     * @param courseDto
     */
    List<TrainCourseVo> selectTrainCourse(CourseDto courseDto);

    /**
     * 根据id查课程信息
     * @param courseId
     * @return
     */
    TrainCourseVo selectTrainCourseById(@Param("courseId") String courseId);

    /**
     * 添加培训课程
     * @param courseDto
     */
    void addTrainCourse(CourseDto courseDto);

    /**
     * 删除培训课程
     * @param courseId
     */
    void deleteTrainCourse(@Param("courseId") String courseId);

    /**
     * 修改培训课程
     * @param courseDto
     */
    void updateTrainCourse(CourseDto courseDto);
    /**
     * 查询所有培训课程科目
     * @return
     */
    List<OptionVo> trainCourseList();

    /**
     * 修改课程时回显
     * @param courseId
     * @return
     */
    CourseVo echoCourse(@Param("courseId") String courseId);

    Integer selectTrainCourseNum();

    /**
     * 该课程是否存在文件
     * @param courseId
     * @return
     */
    List<CourseAllUrl> isEmptyFile(@Param("courseId") String courseId);

    /**
     * 该章节是否含有文件
     * @param sectionId
     * @return
     */
    Integer isEmptyFileBySection(String sectionId);
}
