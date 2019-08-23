package com.rs.teach.mapper.section.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-23 11:25
 */
@Data
public class TotleSectionDto implements Serializable {

    private String id;    //主键
    private String isTrain;//判断是否是培训课程(1为培训课程，0为课程)
    private String[] totleSectionName;  //大章节name数组
    private String totleSectionNameforUpdate;   //用于修改时的大章节name
    private String courseId;      //课程id
}
