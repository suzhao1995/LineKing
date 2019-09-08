package com.rs.teach.service.backstage;

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
     * @param id
     */
    List<UserTrainDataRela> queryUserTrainDataRela(String id);

    /**
     * 删除考核参与人员(若上传答卷了 则删除答卷)
     * @param userTrainDataRela
     */
    void deleteUserTrainDataRela(UserTrainDataRela userTrainDataRela);
}
