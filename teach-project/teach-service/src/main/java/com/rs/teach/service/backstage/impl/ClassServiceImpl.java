package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.ClassMapper;
import com.rs.teach.mapper.backstage.entity.TFClass;
import com.rs.teach.service.backstage.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 16:35
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public void addClass(TFClass tfClass) {
        classMapper.addClass(tfClass);
    }

    @Override
    public void deleteClass(String classId) {
        classMapper.deleteClass(classId);
    }

    @Override
    public void updateClass(TFClass tfClass) {
        classMapper.updateClass(tfClass);
    }

    @Override
    public List<TFClass> selectClass() {
        return classMapper.selectClass();
    }

    @Override
    public TFClass selectClassBySchoolId(TFClass tfClass) {
        return classMapper.selectClassBySchoolId(tfClass);
    }
}
