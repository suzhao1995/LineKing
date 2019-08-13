package com.rs.teach.mapper.section.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 16:53
 */
@Data
public class TrainSectionVo implements Serializable {

    private static final long serialVersionUID = -442561324686250187L;

    //培训大章节序号
    private String trainSectionSort;

    //培训大章节名称
    private String trainSectionName;

    //大章节下的小章节集合
    private List<TrainLitterSectionVo> trainLitterSectionVoList;


}
