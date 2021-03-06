package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 11:01
 */
public interface UserTrainDataRelaMapper {
    /**
     * 批量插入考核人员与考核文件关联表
     *
     * @param userTrainDataRela
     */
    void addUserTrainData(UserTrainDataRela userTrainDataRela);

    /**
     * 添加关联表（USER_TRAIN_DATA_RELA）考核人员上传答卷表id
     *
     * @param userTrainDataRela
     */
    void updateUserTrainData(UserTrainDataRela userTrainDataRela);

    /**
     * 是否有考核权限
     *
     * @param id
     * @param trainCourseId
     * @param userid
     * @return
     */
    Integer isEmpty(@Param("id") String id, @Param("trainCourseId") String trainCourseId, @Param("userid") String userid);
    /**
     * 是否重复提交
     * @param id
     * @param trainCourseId
     * @param userid
     * @return
     */
    String answerSheetIdIsEmpty(@Param("id") String id, @Param("trainCourseId") String trainCourseId, @Param("userid") String userid);

    /**
     * 查询考核人信息
     * @param trainData
     */
    List<UserTrainDataRela> queryUserTrainDataRela( TrainData trainData);

    /**
     * 查询指派人信息
     * @param id
     * @return
     */
    List<UserTrainDataRela> queryAdminName(@Param("id")String id);

    /**
     * 删除参与人员
     * @param id
     */
    void delete(@Param("id") String id);

    /**
     * 删除参与人员 以及关联的答卷表数据
     * @param id
     */
    void deleteRela(@Param("id") String id);

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
    void updateAnswerSheetId(@Param("id") String id, @Param("trainCourseId") String trainCourseId, @Param("userid") String userid);

    /**
     * 是否以及上传了考卷
     * @param id
     * @param trainCourseId
     * @param userid
     * @return
     */
    String selectAnswerSheetId(@Param("id") String id, @Param("trainCourseId") String trainCourseId, @Param("userid") String userid);
}
