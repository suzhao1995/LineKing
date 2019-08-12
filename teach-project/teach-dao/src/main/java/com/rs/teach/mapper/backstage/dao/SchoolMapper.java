package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.School;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 11:44
 */
public interface SchoolMapper {
    void addSchool(School school);

    void deleteSchool(@Param("schoolId") String schoolId);

    void updateSchool(School school);

    List<School> selectSchool();

}
