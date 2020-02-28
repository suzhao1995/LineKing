package com.rs.teach.service.grade;

import com.rs.teach.mapper.grade.dto.GradeDto;
import com.rs.teach.mapper.grade.vo.GradeVo;

/**
 * @author wanghang
 * @Description
 * @create 2020-02-27 0:20
 */
public interface GradeService {
    GradeVo selectQuestion(GradeDto gradeDto);

    GradeVo selectQuestionOne(GradeDto gradeDto);
}
