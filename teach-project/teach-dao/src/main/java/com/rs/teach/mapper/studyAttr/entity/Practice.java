package com.rs.teach.mapper.studyAttr.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description 作业表
 * @create 2019-08-12 10:32
 */
@Data
public class Practice implements Serializable {

    private static final long serialVersionUID = 2763174998668130557L;

    private String pid;              //主键ID
    private String practiceId;     //作业ID
    private String practiceFileName;   //作业文件上传name
    private String practiceUrl;    //作业文件url

}
