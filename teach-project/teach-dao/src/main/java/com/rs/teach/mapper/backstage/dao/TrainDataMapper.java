package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.vo.TrainDataAndAnswerVo;
import com.rs.teach.mapper.backstage.vo.TrainDataFileAllUrlVo;
import com.rs.teach.mapper.backstage.vo.TrainDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-28 16:07
 */
public interface TrainDataMapper {
    /**
     * 添加培训考核文件
     * @param trainData
     */
    void addTrainData(TrainData trainData);
    /**
     * 修改培训考核
     * @param trainData
     */
    void UpdateTrainData(TrainData trainData);
    /**
     * 获取之前培训考核文件路径
     * @param id
     * @return
     */
    TrainData selectTrainDataById(@Param("id") String id);

    /**
     * 查询所有的培训考核文件（后台）
     * @return
     * @param trainDataName
     */
    List<TrainData> selectTrainData(@Param("trainDataName") String trainDataName);

    /**
     * 查询所有的培训考核文件(前台)
     * @return
     * @param userId
     */
    List<TrainData> trainDataAll(@Param("userId") String userId);
    /**
     * 删除培训考核文件
     * @param id
     */
    void trainDataDelete(@Param("id") String id);

    /**
     * 查询考核系列文件的本地地址
     * @param id
     * @return
     */
    TrainDataFileAllUrlVo selectFileAllUrl(@Param("id") String id);
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
    List<TrainDataAndAnswerVo> queryTrainDataAndAnswer(@Param("id") String id);

    List<TrainDataVo> selectTrainDataVo();

    /**
     * 我的考核
     * @return
     * @param userId
     */
    List<TrainData> myTrainData(@Param("userId") String userId);
}
