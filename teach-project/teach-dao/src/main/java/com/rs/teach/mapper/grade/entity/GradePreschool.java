package com.rs.teach.mapper.grade.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 数据库表：GRADE_PRESCHOOL
 * 
 * @author 等级题目
 * @create 2020-02-27
 */
@Data
public class GradePreschool implements Serializable {
    /**
     * null
     */
    private Integer preschoolId;

    /**
     * 题目
     */
    private String question;

    /**
     * 题号
     */
    private Integer sort;

    /**
     * 题目音频
     */
    private String questionAudio;

    /**
     * 正确答案
     */
    private String rightAnswer;

    /**
     * 题目类型
     */
    private String questionType;

    /**
     * 题目图片
     */
    private String questionPicture;

    /**
     * GRADE_PRESCHOOL
     */
    private static final long serialVersionUID = 1L;

}