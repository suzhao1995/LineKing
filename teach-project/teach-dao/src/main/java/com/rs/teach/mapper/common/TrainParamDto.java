package com.rs.teach.mapper.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-13 15:09
 */
@Data
public class TrainParamDto implements Serializable {

    private static final long serialVersionUID = 588316436831780228L;

    /**
     * 课程Id
     */
    private String courseId;
    /**
     * 章节Id
     */
    private String sectionId;
}
