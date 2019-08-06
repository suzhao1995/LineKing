package com.rs.teach.service.training.impl;

import com.rs.teach.mapper.section.dao.TrainSectionMapper;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.service.training.TrainSectionService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:21
 */
@Service
public class TrainSectionServiceImpl implements TrainSectionService {

    @Autowired
    private TrainSectionMapper trainSectionMapper;

    @Override
    public List<TrainSectionVo> selectCourseSection(String courseId) {

        //查询全部课程的章节
        List<TrainSectionVo> list=trainSectionMapper.selectCourseSection(courseId);

        return list;
    }

    @Override
    public TrainSection selectTrainSection(String sectionId) {

        return trainSectionMapper.selectTrainSection(sectionId);
    }

    @Override
    public List<TrainSection> selectSectionList(String trainCourseId, String trainSectionSort) {
        return trainSectionMapper.selectSectionList(trainCourseId,trainSectionSort);
    }

}
