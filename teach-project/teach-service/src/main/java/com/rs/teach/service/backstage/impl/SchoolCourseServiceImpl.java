package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.SchoolCourseMapper;
import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.service.backstage.SchoolCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-27 17:40
 */
@Service
public class SchoolCourseServiceImpl implements SchoolCourseService {

    @Autowired
    private SchoolCourseMapper schoolCourseMapper;

    @Override
    public String[] echoCourse(String schoolId) {

        return schoolCourseMapper.echoCourse(schoolId);
    }

    @Override
    public void addVideoInSchool(School school) {
        //修改学校授权课程
        if (schoolCourseMapper.count(school.getSchoolId()) > 0) {
            schoolCourseMapper.deleteSchoolCourse(school.getSchoolId());
        }
        schoolCourseMapper.addSchoolCourse(school);
    }
}
