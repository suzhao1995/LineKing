package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;
import org.apache.ibatis.annotations.Param;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-04 18:23
 */
public interface TrainDataAnswerMapper {
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
    TrainDataAnswer selectTrainDataAnswer(@Param("id") String id);
    /**
     * 获取之前答案文件信息根据answerId
     * @param answerId
     * @return
     */
    TrainDataAnswer selectTrainDataAnswerByAnswerId(@Param("answerId")String answerId);

    /**
     * 修改答案表
     * @param trainDataAnswer
     */
    void updateTrainDataAnswer(TrainDataAnswer trainDataAnswer);
}
