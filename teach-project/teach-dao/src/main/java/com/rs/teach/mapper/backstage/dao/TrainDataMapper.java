package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.TrainData;
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
     * 查询所有的培训考核文件
     * @return
     */
    List<TrainData> selectTrainData();

    /**
     * 删除培训考核文件
     * @param id
     */
    void trainDataSelect(@Param("id") String id);
}
