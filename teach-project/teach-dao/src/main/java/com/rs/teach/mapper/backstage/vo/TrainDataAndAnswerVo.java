package com.rs.teach.mapper.backstage.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-06 15:48
 */
@Data
public class TrainDataAndAnswerVo implements Serializable {
    private static final long serialVersionUID = 1105886683795793085L;

    private String id;   //主键id
    private String trainDataName;   //培训考核标题名
    private String trainDataFileName; //培训考核上传文件名
    private String trainDataType;  //培训考核文件格式类型
    private String trainCourseId;  //培训课程id
    private String trainCourseName;  //培训课程name
    private String answerId;   //考核文件答案主键id
    private String trainAnswerFileName;  //答案上传文件名(用于下载时给用户返回的文件名)
    private String trainAnswerType;   //文件格式类型

}
