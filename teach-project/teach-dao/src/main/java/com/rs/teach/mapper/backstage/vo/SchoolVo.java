package com.rs.teach.mapper.backstage.vo;

import com.rs.teach.mapper.backstage.entity.SchoolCourseRela;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-26 19:17
 */
@Data
public class SchoolVo implements Serializable {

    private static final long serialVersionUID = -2316323816435943880L;

    private String schoolId;      //学校ID
    private String schoolName;    //学校name
    private String schoolAddress;     //学校地址
    private Integer classNum;   //班级数量
    private Integer teacherNum;  //教师用户数量
    private List<SchoolCourseRela> list; //当前学校所授权的课程


}
