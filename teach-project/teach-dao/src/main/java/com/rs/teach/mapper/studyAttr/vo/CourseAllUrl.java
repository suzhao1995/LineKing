package com.rs.teach.mapper.studyAttr.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-25 18:26
 */
@Data
public class CourseAllUrl implements Serializable {

    private static final long serialVersionUID = -2620140173920469302L;

    private String courseUrl;
    private String practiceUrl;
    private String testpaperUrl;
}
