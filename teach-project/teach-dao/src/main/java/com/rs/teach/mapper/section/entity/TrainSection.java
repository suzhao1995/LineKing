package com.rs.teach.mapper.section.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Section.java
 *
 * @Description:培训章节实体类
 * @author: suzhao
 * @date: 2019年7月31日 下午1:27:12
 * @version: V1.0
 */
@Data
@ToString
public class TrainSection implements Serializable {


    private static final long serialVersionUID = -9077308020974792754L;

    private String trainSectionId;    //培训章节id
    private String trainCourseId;    //培训课程资源id
    private String trainSectionSortid;    //培训大章节id
    private String trainSectionSort;    //培训大章节序号
    private String trainSectionName;    //培训大章节名
    private String trainLitterSectionSort;   //培训小章节序号
    private String trainLitterSectionName;   //培训小章节name
    private String trainLitterSectionUrl;    //培训小章节文件url
    private String trainLitterSectionType;   //培训小章节type(ppt,pdf,word)
    private String updateFileName;            //文件上传name
    private String practiceId;	//作业url
    private String testpaperId;	//试卷url
    private String coursewareId; //课件url

    private String practiceUrl;//作业文件映射全部路径
    private String testpaperUrl;//试卷文件映射全部路径
    private String coursewareUrl;//课件文件映射全部路径

    private String extend1;    //预留字段
    private String extend2;
    private String extend3;
    private String extend4;
}