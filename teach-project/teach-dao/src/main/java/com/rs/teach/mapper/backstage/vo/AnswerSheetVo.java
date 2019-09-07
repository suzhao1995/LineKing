package com.rs.teach.mapper.backstage.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanghang
 * @Description  考核人员上传答卷表TF_EL_ANSWER_SHEET
 * @create 2019-09-05 12:10
 */
@Data
public class AnswerSheetVo implements Serializable {

    private static final long serialVersionUID = -4190584026315639335L;

    private String answerSheetId; //主键id
    private String userId;//用户id
    private String userName;//用户name
    private String trainSheetId;//文件编号
    private String trainSheetUrl;//本地地址
    private String trainSheetPath;//文件映射地址
    private String trainSheetType;//文件格式类型
    private String addTime;//文件添加时间
    private String trainSheetFileName;//上传文件名
    private String evaluationId;//考核评价id
    private String trainCourseId;//考核课程（绑定培训课程id）

    private String grade;   //成绩
    private String evaluation; //评价
}
