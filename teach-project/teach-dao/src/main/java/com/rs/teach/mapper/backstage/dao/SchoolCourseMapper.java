package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.mapper.backstage.entity.SchoolCourseRela;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-27 17:42
 */
public interface SchoolCourseMapper {

    /**
     * 添加学校和课程关联表
     * @param school
     */
    void addSchoolCourse(School school);

    /**
     * 根据校区id查询所授权课程
     * @param schoolId
     */
    List<SchoolCourseRela> selectCourseBySchoolId(@Param("schoolId") String schoolId);

    /**
     * 删除关联表
     * @param schoolId
     */
    void deleteSchoolCourse(@Param("schoolId") String schoolId);
}
