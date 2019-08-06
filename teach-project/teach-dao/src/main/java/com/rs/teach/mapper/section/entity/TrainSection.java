package com.rs.teach.mapper.section.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * Section.java
 *
 * @Description:章节实体类
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
    private String trainSectionSort;    //培训大章节序号
    private String trainSectionName;    //培训大章节名
    private String train_litter_section_sort;   //培训小章节序号
    private String train_litter_section_name;   //培训小章节name
    private String train_litter_section_url;    //培训小章节url
    private String train_litter_section_type;   //培训小章节type(ppt,pdf,word)
    private String update_file_name;            //文件上传name
    private String extend1;    //预留字段
    private String extend2;
    private String extend3;
    private String extend4;


}