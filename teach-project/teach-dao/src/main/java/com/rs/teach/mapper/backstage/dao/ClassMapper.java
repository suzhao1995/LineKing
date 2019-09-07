package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.TFClass;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询此校区的班级数量
     * @param schoolId
     * @return
     */
    Integer queryNumBySchoolId(@Param("schoolId") String schoolId);

    /**
     * 根据校区id查询班级信息
     * @param tfClass
     * @return
     */
    List<TFClass> selectClassBySchoolId(TFClass tfClass);
}
