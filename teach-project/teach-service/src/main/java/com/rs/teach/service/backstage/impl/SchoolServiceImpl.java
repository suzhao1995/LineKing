package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.SchoolMapper;
import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.service.backstage.SchoolService;
import org.springframework.aop.scope.ScopedObject;
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

    @Override
    public void addSchool(School school) throws Exception{

        schoolMapper.addSchool(school);
    }

    @Override
    public void deleteSchool(String schoolId) {
        schoolMapper.deleteSchool(schoolId);
    }

    @Override
    public void updateSchool(School school) {
     schoolMapper.updateSchool(school);
    }

    @Override
    public List<School> selectSchool() {
        List<School> list = schoolMapper.selectSchool();
        return list;
    }
}
