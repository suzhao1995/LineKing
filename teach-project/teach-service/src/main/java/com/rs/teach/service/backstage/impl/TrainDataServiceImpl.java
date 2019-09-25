package com.rs.teach.service.backstage.impl;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.backstage.dao.TrainDataAnswerMapper;
import com.rs.teach.mapper.backstage.dao.TrainDataMapper;
import com.rs.teach.mapper.backstage.dao.UserTrainDataRelaMapper;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;
import com.rs.teach.mapper.backstage.vo.TrainDataAndAnswerVo;
import com.rs.teach.mapper.backstage.vo.TrainDataFileAllUrlVo;
import com.rs.teach.mapper.backstage.vo.TrainDataVo;
import com.rs.teach.service.backstage.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-28 15:52
 */
@Service
public class TrainDataServiceImpl implements TrainDataService {

    @Autowired
    private TrainDataMapper trainDataMapper;
    @Autowired
    private TrainDataAnswerMapper trainDataAnswerMapper;
    @Autowired
    private UserTrainDataRelaMapper userTrainDataRelaMapper;

    @Override
    public void addTrainData(TrainData trainData) {
        trainDataMapper.addTrainData(trainData);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void UpdateTrainData(TrainData trainData, TrainDataAnswer trainDataAnswer) {
        try {
            trainDataMapper.UpdateTrainData(trainData);
            if (StrUtil.isNotEmpty(trainDataAnswer.getAnswerId())) {
                trainDataAnswerMapper.updateTrainDataAnswer(trainDataAnswer);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public TrainData selectTrainDataById(String id) {
        return trainDataMapper.selectTrainDataById(id);
    }

    @Override
    public List<TrainData> selectTrainData(String trainDataName) {
        return trainDataMapper.selectTrainData(trainDataName);
    }

    @Override
    public void trainDataDelete(String id) {
        trainDataMapper.trainDataDelete(id);
    }

    @Override
    public TrainDataFileAllUrlVo selectFileAllUrl(String id) {
        TrainDataFileAllUrlVo vo = trainDataMapper.selectFileAllUrl(id);
        //是否存在
        return vo;
    }

    @Override
    public List<TrainData> queryTrainDataCourseId() {
        return trainDataMapper.queryTrainDataCourseId();
    }

    @Override
    public List<TrainDataAndAnswerVo> queryTrainDataAndAnswer(String id) {
        return trainDataMapper.queryTrainDataAndAnswer(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addTrainDataAll(TrainData trainData, TrainDataAnswer trainDataAnswer) {
        try {
            trainDataMapper.addTrainData(trainData);
            //考核文件答案添加
            trainDataAnswerMapper.addTrainDataAnswer(trainDataAnswer);
            //考核人员与考核文件关联表添加
            /*userTrainDataRelaMapper.addUserTrainData(userTrainDataRela);*/
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<TrainDataVo> selectTrainDataVo() {
        return trainDataMapper.selectTrainDataVo();
    }
}
