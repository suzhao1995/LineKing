package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.mapper.backstage.vo.TrainDataAndAnswerVo;
import com.rs.teach.mapper.backstage.vo.TrainDataFileAllUrlVo;
import com.rs.teach.mapper.backstage.vo.TrainDataVo;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-28 15:52
 */
public interface TrainDataService {
    /**
     * 添加培训考核文件（全部参数）
     * @param trainData
     */
    void addTrainData(TrainData trainData);

    /**
     * 修改培训考核和答案文件
     * @param trainData
     * @param trainDataAnswer
     */
    void UpdateTrainData(TrainData trainData, TrainDataAnswer trainDataAnswer);

    /**
     * 获取之前培训考核文件路径
     * @param id
     * @return
     */
    TrainData selectTrainDataById(String id);

    /**
     * 查询所有的培训考核文件
     * @return
     * @param trainDataName
     */
    List<TrainData> selectTrainData(String trainDataName);

    /**
     * 删除培训考核文件
     * @param id
     */
    void trainDataDelete(String id);

    /**
     * 查询考核系列文件的本地地址
     * @param id
     * @return
     */
    TrainDataFileAllUrlVo selectFileAllUrl(String id);

    /**
     * 考核科目回显（考核文件表存在的科目）
     * @return
     */
    List<TrainData> queryTrainDataCourseId();

    /**
     * 培训考核文件修改回显
     * @param id
     * @return
     */
    List<TrainDataAndAnswerVo> queryTrainDataAndAnswer(String id);

    /**
     * //添加考核系列文件（考核文件添加，考核文件答案添加，考核人员与考核文件关联表添加）
     * @param trainData
     * @param trainDataAnswer
     * @param userTrainDataRela
     */
    void addTrainDataAll(TrainData trainData, TrainDataAnswer trainDataAnswer, UserTrainDataRela userTrainDataRela);

    List<TrainDataVo> selectTrainDataVo();
}
