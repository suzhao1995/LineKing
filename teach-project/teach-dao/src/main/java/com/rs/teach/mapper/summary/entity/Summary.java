package com.rs.teach.mapper.summary.entity;

import lombok.Data;

/**
 * @author 汪航
 * @Description  课后总结表
 * @create 2019-08-07 12:55
 */
@Data
public class Summary {

    private String userId; //用户id
    private String sectionSort;  //大章节序号
    private String courseId;   //课程id
    private String classId;    //班级id
    private String summary;    //课程笔记
    private String extend1;	//预留字段
    private String extend2;	//
    private String extend3;	//
    private String extend4;	//

}
