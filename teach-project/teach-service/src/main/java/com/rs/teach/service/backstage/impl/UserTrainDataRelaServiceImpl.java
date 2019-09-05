package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.UserTrainDataRelaMapper;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.service.backstage.UserTrainDataRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 11:00
 */
@Service
public class UserTrainDataRelaServiceImpl implements UserTrainDataRelaService {

    @Autowired
    private UserTrainDataRelaMapper userTrainDataRelaMapper;

    @Override
    public Integer isEmpty(String id, String trainCourseId, String userid) {
        return userTrainDataRelaMapper.isEmpty(id, trainCourseId, userid);
    }

    @Override
    public void addUserTrainData(UserTrainDataRela userTrainDataRela) {
        userTrainDataRelaMapper.addUserTrainData(userTrainDataRela);
    }
}
