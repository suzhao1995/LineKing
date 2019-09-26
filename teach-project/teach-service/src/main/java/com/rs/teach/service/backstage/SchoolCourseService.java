package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.School;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-27 17:40
 */
public interface SchoolCourseService {

    /**
     * 权限课程回显
     * @param schoolId
     * @return
     */
    String[] echoCourse(String schoolId);

    /**
     * 给学校添加视频课程
     * @param school
     */
    void addVideoInSchool(School school);
}
