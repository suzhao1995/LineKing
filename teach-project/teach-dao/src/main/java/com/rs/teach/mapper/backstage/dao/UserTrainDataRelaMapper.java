package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import org.apache.ibatis.annotations.Param;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 11:01
 */
public interface UserTrainDataRelaMapper {
    /**
     * 批量插入考核文件和考核人员表
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
}
