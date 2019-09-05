package com.rs.teach.mapper.backstage.entity;

import com.rs.teach.mapper.common.PageDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 16:36
 */
@Data
public class TFClass extends PageDto implements Serializable {
    private static final long serialVersionUID = 6113165796855823273L;

    private String classId;   //班级id（主键）
    private String schoolId;  //学校id
    private String schoolName;  //学校名称
    private String className;  //班级名称
}
