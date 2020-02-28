package com.rs.teach.mapper.grade.dao;

import com.rs.teach.mapper.grade.entity.GradePreschoolOption;
import java.math.BigDecimal;

public interface GradePreschoolOptionMapper {
    /**
     *  根据主键删除数据库的记录,GRADE_PRESCHOOL_OPTION
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(BigDecimal id);

    /**
     *  新写入数据库记录,GRADE_PRESCHOOL_OPTION
     *
     * @param record
     * @return
     */
    int insert(GradePreschoolOption record);

    /**
     *  动态字段,写入数据库记录,GRADE_PRESCHOOL_OPTION
     *
     * @param record
     * @return
     */
    int insertSelective(GradePreschoolOption record);

    /**
     *  根据指定主键获取一条数据库记录,GRADE_PRESCHOOL_OPTION
     *
     * @param id
     * @return
     */
    GradePreschoolOption selectByPrimaryKey(BigDecimal id);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,GRADE_PRESCHOOL_OPTION
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(GradePreschoolOption record);

    /**
     *  根据主键来更新符合条件的数据库记录,GRADE_PRESCHOOL_OPTION
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(GradePreschoolOption record);
}