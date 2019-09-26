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
     * 添加学校和课程关联表(给学校添加视频课程)
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

    /**
     * 该学校是否添加了课程
     * @param schoolId
     * @return
     */
    Integer count(@Param("schoolId") String schoolId);

    /**
     * 权限课程回显
     * @param schoolId
     * @return
     */
    String[] echoCourse(@Param("schoolId") String schoolId);

}
