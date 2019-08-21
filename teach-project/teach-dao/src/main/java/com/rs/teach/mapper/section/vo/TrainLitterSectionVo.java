package com.rs.teach.mapper.section.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-13 17:31
 */
@Data
public class TrainLitterSectionVo implements Serializable {

    private static final long serialVersionUID = 1919373047483607773L;

    //培训章节id
    private String trainSectionId;

    //培训小章节序号
    private String trainLitterSectionSort;

    //培训小章节name
    private String trainLitterSectionName;

    //培训小章节url
    private String trainLitterSectionUrl;

    //培训小章节type
    private String trainLitterSectionType;

    //文件上传目录
    private String updateFileName;

    //课件文件全部路径
    private String coursewareUrl;

    //课件ID
    private String coursewareId;

    private String pid;              //作业主键
    private String practiceId;     //作业ID
    private String practiceFileName;   //作业文件上传name
    private String practiceUrl;    //作业文件url
    private String practicePath;    //作业映射路径

    private String tid;              //考试主键
    private String testpaperId;     //考试ID
    private String testpaperName;   //考试文件上传name
    private String testpaperUrl;    //考试文件url
    private String testpaperPath;    //考试映射路径
}
