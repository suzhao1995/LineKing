package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.TrainDataMapper;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.vo.TrainDataFileAllUrlVo;
import com.rs.teach.service.backstage.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void addTrainData(TrainData trainData) {
        trainDataMapper.addTrainData(trainData);
    }

    @Override
    public void UpdateTrainData(TrainData trainData) {
        trainDataMapper.UpdateTrainData(trainData);
    }

    @Override
    public TrainData selectTrainDataById(String id) {
        return trainDataMapper.selectTrainDataById(id);
    }

    @Override
    public List<TrainData> selectTrainData() {
        return trainDataMapper.selectTrainData();
    }

    @Override
    public void trainDataDelete(String id) {
        trainDataMapper.trainDataDelete(id);

    }

    @Override
    public TrainDataFileAllUrlVo selectFileAllUrl(String id) {
        return trainDataMapper.selectFileAllUrl(id);
    }
}
