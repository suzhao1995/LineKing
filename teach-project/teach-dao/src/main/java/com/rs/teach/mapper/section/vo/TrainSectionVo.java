package com.rs.teach.mapper.section.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 16:53
 */
@Data
public class TrainSectionVo implements Serializable {

    private static final long serialVersionUID = -442561324686250187L;

    //培训章节id
    private String trainSectionId;

    //培训课程id
    private String trainCourseId;

    //培训课程Name
    private String trainCourseName;

    //培训课程封面URL
    private String picUrl;

    //培训章节总数
    private String trainSectionNumber;


    //培训大章节序号
    private String trainSectionSort;

    //培训大章节名称
    private String trainSectionName;


    //培训小章节序号
    private String trainLitterSectionSort;

    //培训小章节name
    private String trainLitterSectionName;

    //培训小章节url
    private String trainLitterSectionUrl;

    //培训小章节type
    private String trainLitterSectionType;

}
