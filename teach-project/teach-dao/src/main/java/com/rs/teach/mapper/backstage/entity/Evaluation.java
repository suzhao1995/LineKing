package com.rs.teach.mapper.backstage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanghang
 * @Description  考核评价表
 * @create 2019-09-05 16:38
 */
@Data
public class Evaluation implements Serializable {


    private static final long serialVersionUID = -5408781243878413876L;

    private String evaluationId;  //主键ID
    private String grade;   //成绩
    private String evaluation; //评价
}
