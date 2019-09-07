package com.rs.teach.service.backstage.impl;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.backstage.dao.UserTrainDataRelaMapper;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.service.backstage.UserTrainDataRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String answerSheetIdIsEmpty(String id, String trainCourseId, String userid) {
        return userTrainDataRelaMapper.answerSheetIdIsEmpty( id,  trainCourseId,  userid);
    }

    @Override
    public void addUserTrainData(UserTrainDataRela userTrainDataRela) {
        userTrainDataRelaMapper.addUserTrainData(userTrainDataRela);
    }

    @Override
    public List<UserTrainDataRela> queryUserTrainDataRela(String id) {
        List<UserTrainDataRela> userTrainDataRelas = userTrainDataRelaMapper.queryUserTrainDataRela(id);
        List<UserTrainDataRela> vo = userTrainDataRelaMapper.queryAdminName(id);

        return userTrainDataRelas;
    }

    @Override
    public void deleteUserTrainDataRela(UserTrainDataRela userTrainDataRela) {
        if(StrUtil.equalsIgnoreCase("0",userTrainDataRela.getAnswerSheetId())){
            userTrainDataRelaMapper.delete(userTrainDataRela.getId());
        }
        //用户上传答卷关联删除
        userTrainDataRelaMapper.deleteRela(userTrainDataRela.getId());
    }
}
