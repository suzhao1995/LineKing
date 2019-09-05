package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;

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
     * 批量插入考核文件和考核人员表
     * @param userTrainDataRela
     */
    void addUserTrainData(UserTrainDataRela userTrainDataRela);
}
