package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.TFClass;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 16:37
 */
public interface ClassMapper {
    void addClass(TFClass tfClass);

    void deleteClass(String classId);

    void updateClass(TFClass tfClass);

    List<TFClass> selectClass();
}
