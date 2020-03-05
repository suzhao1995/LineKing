package com.rs.teach.mapper.grade.dao;

import com.rs.teach.mapper.grade.dto.GradeDto;
import com.rs.teach.mapper.grade.entity.GradePreschool;
import com.rs.teach.mapper.grade.entity.GradePreschoolOption;
import com.rs.teach.mapper.grade.vo.GradeVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface GradePreschoolMapper {
    /**
     *  根据主键删除数据库的记录,GRADE_PRESCHOOL
     *
     * @param preschoolId
     * @return
     */
    int deleteByPrimaryKey(BigDecimal preschoolId);

    /**
     *  新写入数据库记录,GRADE_PRESCHOOL
     *
     * @param record
     * @return
     */
    int insert(GradePreschool record);

    /**
     *  动态字段,写入数据库记录,GRADE_PRESCHOOL
     *
     * @param record
     * @return
     */
    int insertSelective(GradePreschool record);

    /**
     *  根据指定主键获取一条数据库记录,GRADE_PRESCHOOL
     *
     * @param preschoolId
     * @return
     */
    GradePreschool selectByPrimaryKey(BigDecimal preschoolId);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,GRADE_PRESCHOOL
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(GradePreschool record);

    /**
     *  根据主键来更新符合条件的数据库记录,GRADE_PRESCHOOL
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(GradePreschool record);

    Integer addBaby(GradeDto gradeDto);

    GradeVo selectQuestion(GradeDto gradeDto);

    List<GradePreschoolOption> selectAnswers(@Param("preschoolId") Integer preschoolId);

    void addSheet(GradeDto gradeDto);

    Integer selectCount(@Param("babyId") Integer babyId);

    List<GradePreschool> selectAll();

    void addQuestionAudio(@Param("list") List<GradePreschool> list);

    void addAudio(GradePreschool gradePreschool);
}