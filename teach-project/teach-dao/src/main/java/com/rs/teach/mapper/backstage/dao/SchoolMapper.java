package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.mapper.backstage.vo.SchoolVo;
import com.rs.teach.mapper.common.OptionVo;
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

    List<OptionVo> selectSchool();

    List<SchoolVo> selectSchoolVo();

    /**
     * 查询新插入数据的schoolId
     * @param school
     * @return
     */
    String selectSchoolId(School school);

    Integer isEmpty(School school);

    /**
     * 根据学校id查询数据
     * @param school
     * @return
     */
    SchoolVo selectSchoolBySchoolId(School school);
}
