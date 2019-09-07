package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.TrainDataAnswerMapper;
import com.rs.teach.mapper.backstage.dao.TrainDataMapper;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;
import com.rs.teach.service.backstage.TrainDataAnswerService;
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
public class TrainDataAnswerServiceImpl implements TrainDataAnswerService {

    @Autowired
    private TrainDataAnswerMapper trainDataAnswerMapper;


    @Override
    public void addTrainDataAnswer(TrainDataAnswer trainDataAnswer) {
        trainDataAnswerMapper.addTrainDataAnswer(trainDataAnswer);
    }

    @Override
    public TrainDataAnswer selectTrainDataAnswer(String id) {
        return trainDataAnswerMapper.selectTrainDataAnswer(id);
    }

    @Override
    public TrainDataAnswer selectTrainDataAnswerByAnswerId(String answerId) {
        return trainDataAnswerMapper.selectTrainDataAnswerByAnswerId(answerId);
    }
}
