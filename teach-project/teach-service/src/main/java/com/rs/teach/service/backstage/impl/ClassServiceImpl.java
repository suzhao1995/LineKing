package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.ClassMapper;
import com.rs.teach.mapper.backstage.entity.TFClass;
import com.rs.teach.service.backstage.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addclass(TFClass tfClass) {
        classMapper.addclass(tfClass);
    }
}
