package com.rs.teach.mapper.backstage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-23 11:25
 */
@Data
public class TotleSection implements Serializable {

    private String id ;  //主键ID
    private String totleSectionSort ;  //大章节序号
    private String totleSectionName;  //大章节name
    private String courseId;      //课程id
}
