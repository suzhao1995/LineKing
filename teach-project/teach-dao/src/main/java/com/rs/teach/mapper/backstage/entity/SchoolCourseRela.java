package com.rs.teach.mapper.backstage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-27 12:41
 */
@Data
public class SchoolCourseRela implements Serializable {
    private static final long serialVersionUID = 7996868479379220648L;

    private String SchoolId;//学校id
    private String courseId;//课程id
    private String courseFlag;//是否拥有课程（‘1’ 是，‘0’否）

}
