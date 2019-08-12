package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.School;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 11:13
 */
public interface SchoolService {
    /**
     * 新增加判断用户是否为管理员
     * @param school
     */
    void addSchool(School school) throws Exception;

    /**
     * 删除学校
     * @param schoolId
     */
    void deleteSchool(String schoolId);

    /**
     * 修改学校
     * @param school
     */
    void updateSchool(School school);

    /**
     * 分页查询
     */
    List<School> selectSchool();
}
