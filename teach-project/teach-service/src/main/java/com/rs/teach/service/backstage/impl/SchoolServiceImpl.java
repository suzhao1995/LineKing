package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.ClassMapper;
import com.rs.teach.mapper.backstage.dao.SchoolCourseMapper;
import com.rs.teach.mapper.backstage.dao.SchoolMapper;
import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.mapper.backstage.vo.SchoolVo;
import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.mapper.user.dao.UserMapper;
import com.rs.teach.service.backstage.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 11:14
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SchoolCourseMapper schoolCourseMapper;

    @Override
    public void addSchool(School school) {
        //添加学校
        schoolMapper.addSchool(school);
        String schoolId = schoolMapper.selectSchoolId(school);
        //添加学校和课程关联表
        school.setSchoolId(schoolId);
        schoolCourseMapper.addSchoolCourse(school);
    }

    @Override
    public void deleteSchool(String schoolId) {
        schoolMapper.deleteSchool(schoolId);
        schoolCourseMapper.deleteSchoolCourse(schoolId);
    }

    @Override
    public void updateSchool(School school) {
        //修改学校name和address
        schoolMapper.updateSchool(school);
        //修改学校授权课程
        schoolCourseMapper.deleteSchoolCourse(school.getSchoolId());
        schoolCourseMapper.addSchoolCourse(school);
    }

    @Override
    public List<OptionVo> selectSchool() {
        return schoolMapper.selectSchool();
    }

    @Override
    public List<SchoolVo> selectSchoolVo() {
        List<SchoolVo> list = schoolMapper.selectSchoolVo();
        for (SchoolVo vo : list) {
            //查询此校区的班级数量
            vo.setClassNum(classMapper.queryNumBySchoolId(vo.getSchoolId()));
            //查询教师数量
            vo.setTeacherNum(userMapper.queryTeachNumBySchoolId(vo.getSchoolId()));
            //根据校区id查询所授权课程
            vo.setList(schoolCourseMapper.selectCourseBySchoolId(vo.getSchoolId()));
        }
        return list;
    }

    @Override
    public Integer isEmpty(School school) {
        return schoolMapper.isEmpty(school);
    }

    @Override
    public SchoolVo selectSchoolBySchoolId(School school) {
        SchoolVo vo = schoolMapper.selectSchoolBySchoolId(school);
        vo.setList(schoolCourseMapper.selectCourseBySchoolId(school.getSchoolId()));
        return vo;
    }
}
