package com.rs.teach.mapper.section.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description  管理员操作章节接收参数表
 * @create 2019-08-15 14:59
 */
@Data
public class SectionDto implements Serializable {

    private static final long serialVersionUID = 6032990754421261433L;

    //判断是否是培训课程(1为培训课程，0为课程)
    private String isTrain;

    //课程id
    private String courseId;

    //大章节id
    private String sectionSortid;

    //大章节序号
    private String sectionSort;

    //大章节名称
    private String sectionName;

    //小章节序号
    private String litterSectionSort;

    //小章节name
    private String litterSectionName;

    //作业表主键
    private String pid;

    //考试表主键
    private String tid;

    //文件数组
    private MultipartFile[] files;

    private String coursewareUrl;//课件文件全部路径

    /**      下面字段系统生成        */
    //小章节url
    private String litterSectionUrl;

    //文件上传name
    private String updateFileName;

    //小章节type(ppt,pdf,word)
    private String litterSectionType;

    //章节id（即为课件文件的唯一标识）
    private String sectionId;

    //课件ID
    private String coursewareId;

    //作业ID
    private String practiceId;

    //试卷id
    private String testpaperId;

}
