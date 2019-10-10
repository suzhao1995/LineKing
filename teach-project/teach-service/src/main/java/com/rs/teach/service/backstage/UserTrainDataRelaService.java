package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;

import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 11:00
 */
public interface UserTrainDataRelaService {

    /**
     * 是否有考核权限
     * @param id
     * @param trainCourseId
     * @param userid
     * @return
     */
    Integer isEmpty(String id, String trainCourseId, String userid);

    /**
     * 是否重复提交
     * @param id
     * @param trainCourseId
     * @param userid
     * @return
     */
    String answerSheetIdIsEmpty(String id, String trainCourseId, String userid);

    /**
     * 批量插入考核人员与考核文件关联表
     * @param userTrainDataRela
     */
    void addUserTrainData(UserTrainDataRela userTrainDataRela);

    /**
     * 查询考核人信息
     * @param trainData
     */
    List<UserTrainDataRela> queryUserTrainDataRela(TrainData trainData);

    /**
     * 删除考核参与人员(若上传答卷了 则删除答卷)
     * @param userTrainDataRela
     */
    void deleteUserTrainDataRela(UserTrainDataRela userTrainDataRela);

    /**
     * 考核人员回显
     * @param userTrainDataRela
     * @return
     */
    String[] echoPeople(UserTrainDataRela userTrainDataRela);

    /**
     * 修改为我的考核，ANSWER_SHEET_ID为"加入"时即为加入我的考核，不为0和1是就是已提交考核答案文件了
     * @param id
     * @param trainCourseId
     * @param userid
     */
    void updateAnswerSheetId(String id, String trainCourseId, String userid);

    /**
     * 是否以及上传了考卷
     * @param id
     * @param trainCourseId
     * @param userid
     * @return
     */
    boolean isBlank(String id, String trainCourseId, String userid);
}
