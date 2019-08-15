package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.TFClass;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 16:35
 */
public interface ClassService {
    /**
     * 添加班级
     * @param tfClass
     */
    void addClass(TFClass tfClass);

    /**
     * 根据班级ID删除
     * @param classId
     */
    void deleteClass(String classId);

    /**
     * 修改班级
     * @param tfClass
     */
    void updateClass(TFClass tfClass);

    /**
     * @return 分页查询数据
     */
    List<TFClass> selectClass();
}
