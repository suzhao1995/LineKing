package com.rs.teach.mapper.studyAttr.dto;

import com.rs.teach.mapper.common.PageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 汪航
 * @Description 管理员操作课程接收参数表
 * @create 2019-08-14 17:06
 */
@Data
public class CourseDto extends PageDto implements Serializable {

    private static final long serialVersionUID = -5282333731331735980L;

    //判断是否是培训课程(1为培训课程，0为课程)
    private String isTrain;

    //课程ID
    private String courseId;

    //课程名字
    private String courseName;

    //上传图片路径
    private String coursePicUrl;

    //是否含有试卷（1：是；0：否）
    private String courseware;

    //是否含有作业（1：是；0：否）
    private String schoolwork;

    //是否含有试卷（1：是；0：否）
    private String testpaper;

    //课程类型
    private String courseType;

    //校区id
    private String schoolId;

    //课程等级
    private String courseLev;

    //培训课程添加时间
    private String addTime;

    //培训课程科目
    private String subject;

}
