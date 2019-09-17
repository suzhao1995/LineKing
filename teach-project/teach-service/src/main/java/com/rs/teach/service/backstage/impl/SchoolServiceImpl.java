package com.rs.teach.service.backstage.impl;

import cn.hutool.core.util.StrUtil;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteSchool(String schoolId) {
        try {
            schoolMapper.deleteSchool(schoolId);
            schoolCourseMapper.deleteSchoolCourse(schoolId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateSchool(School school) {
        try {
            if (StrUtil.isNotBlank(school.getSchoolAddress()) && StrUtil.isNotBlank(school.getSchoolName())) {
                //修改学校name和address
                schoolMapper.updateSchool(school);
            } else {
                //修改学校授权课程
                if (schoolCourseMapper.count(school.getSchoolId()) > 0) {
                    schoolCourseMapper.deleteSchoolCourse(school.getSchoolId());
                }
                schoolCourseMapper.addSchoolCourse(school);
            }
        } catch (Exception e) {
            throw e;
        }
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
        return vo;
    }

    @Override
    public String selectSchoolName(String schoolId) {
        return schoolMapper.selectSchoolName(schoolId);
    }
}
