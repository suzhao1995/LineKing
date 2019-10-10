package com.rs.teach.service.backstage.impl;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.backstage.dao.UserTrainDataRelaMapper;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.service.backstage.UserTrainDataRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public List<UserTrainDataRela> queryUserTrainDataRela(TrainData trainData) {
        List<UserTrainDataRela> userTrainDataRelas = userTrainDataRelaMapper.queryUserTrainDataRela(trainData);
        List<UserTrainDataRela> vos = userTrainDataRelaMapper.queryAdminName(trainData.getId());
        for (UserTrainDataRela vo1:userTrainDataRelas) {
            for (UserTrainDataRela vo2:vos) {
                if(StrUtil.equalsIgnoreCase(vo1.getId(),vo2.getId())){
                    vo1.setAdminName(vo2.getAdminName());
                }
            }
        }

        return userTrainDataRelas;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserTrainDataRela(UserTrainDataRela userTrainDataRela) {
        try {
            if(StrUtil.equalsIgnoreCase("0",userTrainDataRela.getAnswerSheetId())){
                userTrainDataRelaMapper.delete(userTrainDataRela.getId());
            }else {
                userTrainDataRelaMapper.delete(userTrainDataRela.getId());
                //用户上传答卷关联删除
                userTrainDataRelaMapper.deleteRela(userTrainDataRela.getId());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String[] echoPeople(UserTrainDataRela userTrainDataRela) {
        return userTrainDataRelaMapper.echoPeople(userTrainDataRela);
    }

    @Override
    public void updateAnswerSheetId(String id, String trainCourseId, String userid) {
        userTrainDataRelaMapper.updateAnswerSheetId( id,  trainCourseId,  userid);
    }

    @Override
    public boolean isBlank(String id, String trainCourseId, String userid) {
        String result = userTrainDataRelaMapper.selectAnswerSheetId(id,  trainCourseId,  userid);
        if (StrUtil.equals("0",result) || StrUtil.equals("加入",result)){
            return true;
        }
        return false;
    }
}
