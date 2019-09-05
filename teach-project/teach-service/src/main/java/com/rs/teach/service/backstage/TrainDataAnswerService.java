package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-04 18:20
 */
public interface TrainDataAnswerService {


    /**
     * 考核文件答案添加
     * @param trainDataAnswer
     */
    void addTrainDataAnswer(TrainDataAnswer trainDataAnswer);

    /**
     * 查询当前考核文件的答案
     * @param id
     * @return
     */
    TrainDataAnswer selectTrainDataAnswer(String id);
}
