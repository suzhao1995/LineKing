package com.rs.teach.mapper.section.entity;

import com.rs.teach.mapper.common.Enums.CourseStatusEnum;
import lombok.Data;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 13:24
 */
@Data
public class UserCourseRela {

    //课程id
    private String courseId;

    //用户id
    private String user_Id;

    //章节id
    private String section_Id;

    /**
     *  NO_SIGN(0, "待报名"),
     *  STARTING(1, "学习中"),
     *  END(2, "已学完");
     *  {@link CourseStatusEnum}
     *
     */
    private String isFinish;

    //关联类型（0：章节，1：课程）
    private String relaType;

    //班级Id
    private String class_id;

    //预留字段
    private String extend1;
    private String extend2;
    private String extend3;
    private String extend4;
}
