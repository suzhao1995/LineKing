package com.rs.teach.mapper.note.entity;

import lombok.Data;

/**
 * @author 汪航
 * @Description 笔记表
 * @create 2019-08-06 12:42
 */
@Data
public class CourseNote {

    private String userId; //用户id
    private String sectionId;  //章节id
    private String courseId;   //课程id
    private String classId;    //班级id
    private String note;    //课程笔记
    private String extend1;	//预留字段
    private String extend2;	//
    private String extend3;	//
    private String extend4;	//
}
