package com.rs.teach.mapper.section.dao;

import com.rs.teach.mapper.section.entity.UserCourseRela;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:25
 */
public interface UserCourseRelaMapper {

    int isNotEmpty(@Param("courseId") String courseId, @Param("userId") String userId);

    List<UserCourseRela> selectStatus(@Param("courseId")String courseId, @Param("userId")String userId);
}
