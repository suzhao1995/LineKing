package com.rs.teach.mapper.backstage.entity;

import com.rs.teach.mapper.common.PageDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description  TF_EL_TRAIN_DATA培训考核文件表
 * @create 2019-08-28 12:22
 */
@Data
public class TrainData extends PageDto implements Serializable {
    private static final long serialVersionUID = -4542254441456503741L;

    private String id;   //主键id
    private String trainDataId;   //培训考核文件id
    private String trainDataName;   //培训考核标题名
    private String trainDataFileName; //培训考核上传文件名
    private String trainDataUrl;    //培训考核本地地址
    private String trainDataPath;   //培训考核文件映射地址
    private String trainDataType;  //培训考核文件格式类型
    private String addTime;  //培训考核文件添加时间
    private String trainCourseId;  //培训课程id
    private String trainCourseName;  //培训课程name
    private String answerId;   //考核文件答案主键id

    private String[] userIds;  //考核用户id数组
    private String adminId; //指派人ID
    private String userName; //参与考核人的name

    private String answerSheetId;  //答卷ID
}
