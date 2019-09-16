package com.rs.teach.service.backstage;

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
}
