package com.rs.teach.mapper.backstage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description  培训考核答案表TF_EL_TRAIN_DATA_ANSWER
 * @create 2019-09-04 17:42
 */
@Data
public class TrainDataAnswer implements Serializable {

    private static final long serialVersionUID = 2058432525749650245L;

    private String answerId;   //主键id
    private String answerFileId;   //答案文件编号
    private String trainAnswerName;   //考核标题名
    private String trainAnswerUrl; //文件本地地址
    private String trainAnswerPath;    //文件映射地址
    private String trainAnswerType;   //文件格式类型
    private String addTime;  //文件添加时间
    private String trainDataFileName;  //上传文件名(用于下载时给用户返回的文件名)

}
