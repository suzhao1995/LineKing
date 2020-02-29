package com.rs.teach.mapper.grade.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanghang
 * @Description
 * @create 2020-02-27 0:41
 */
@Data
public class GradeDto implements Serializable {
    private static final long serialVersionUID = -25655982397900326L;

    /**
     * 宝宝姓名
     */
    private String babyName;

    /**
     * 宝宝年龄
     */
    private String babyAge;

    /**
     * 英语等级
     */
    private String englishGrade;

    /**
     * 宝宝填写答案
     */
    private String babyAnswer;

    /**
     * 题目序号
     */
    private Integer sort;

    /**
     * 宝宝id
     */
    private Integer babyId;
}
