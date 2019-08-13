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
}
