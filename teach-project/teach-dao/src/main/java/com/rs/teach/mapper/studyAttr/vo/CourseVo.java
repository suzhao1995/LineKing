package com.rs.teach.mapper.studyAttr.vo;

import com.rs.teach.mapper.common.PageDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description 管理员操作课程接收参数表
 * @create 2019-08-14 17:06
 */
@Data
public class CourseVo implements Serializable {

    //判断是否是培训课程(1为培训课程，0为课程)
    private String isTrain;

    //课程ID
    private String courseId;

    //课程名字
    private String courseName;

    //上传图片路径
    private String coursePicUrl;

    //课程类型
    private String courseType;

    //课程等级
    private String courseLev;

}
