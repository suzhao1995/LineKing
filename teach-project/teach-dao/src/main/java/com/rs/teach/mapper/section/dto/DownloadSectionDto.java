package com.rs.teach.mapper.section.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-17 17:30
 */
@Data
public class DownloadSectionDto implements Serializable {

    private static final long serialVersionUID = 8828637926103670658L;
    //课件url目录
    String sectionUrl ;
    //课件id
    String coursewareId;
    //课件上传名
    String updateFileName ;
    //课件类型
    String sectionType ;
    //练习id
    String workId ;
    //试卷id
    String testPaperId ;
    //章节名
    String sectionName;
}
