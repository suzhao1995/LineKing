package com.rs.teach.mapper.studyAttr.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description 试卷表
 * @create 2019-08-12 10:24
 */
@Data
public class Testpaper implements Serializable {

    private static final long serialVersionUID = 8859218685448453081L;

    private String tid;              //主键ID
    private String testpaperId;     //考试ID
    private String testpaperName;   //考试文件上传name
    private String testpaperUrl;    //考试文件url
}
