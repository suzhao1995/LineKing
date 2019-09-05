package com.rs.teach.mapper.backstage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanghang
 * @Description USER_TRAIN_DATA_RELA考核人员与考核文件关联表
 * @create 2019-09-05 10:31
 */
@Data
public class UserTrainDataRela implements Serializable {
    private static final long serialVersionUID = -4768740745076457074L;

    private String id;   //主键Id
    private String dataId;  // 培训考核文件表主键id
    private String userId;  //考核用户id
    private String adminId;  //指派人id
    private String answerSheetId; //考核人员上传答卷表id
    private String trainCourseId; //考核课程（绑定培训课程id）

    private String[] userIds; //考核用户id数组
}
