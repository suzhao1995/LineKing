package com.rs.teach.mapper.grade.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 数据库表：GRADE_PRESCHOOL_OPTION
 * 
 * @author 题目答案选项
 * @create 2020-02-27
 */
@Data
public class GradePreschoolOption implements Serializable {
    /**
     * null
     */
    private Integer id;

    /**
     * 题目id
     */
    private Integer preschoolId;

    /**
     * 答案选项
     */
    private String answerOption;

    /**
     * 答案图片访问路径
     */
    private String answerPictureUrl;

    /**
     * 答案文字
     */
    private String answerWord;

    /**
     * GRADE_PRESCHOOL_OPTION
     */
    private static final long serialVersionUID = 1L;

}