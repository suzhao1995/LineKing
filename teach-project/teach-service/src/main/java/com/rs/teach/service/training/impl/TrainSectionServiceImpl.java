package com.rs.teach.service.training.impl;

import com.rs.teach.mapper.section.dao.TrainSectionMapper;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.section.vo.TrainLitterSectionVo;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.dao.TrainCourseMapper;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.training.TrainSectionService;
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

    @Autowired
    private TrainCourseMapper trainCourseMapper;

    @Override
    public TrainCourseVo selectCourseSection(String courseId) {

        TrainCourseVo trainCourseVo = trainCourseMapper.selectTrainCourseById(courseId);

//        List<TrainCourseVo> list = trainSectionMapper.selectCourseSection(courseId);
        //查询此课程的大章节
        List<TrainSectionVo> trainSectionVoList = trainSectionMapper.selectTrainSectionById(courseId);
        for (TrainSectionVo vo : trainSectionVoList) {
            //查询小章节
            List<TrainLitterSectionVo> trainLitterSectionVoList = trainSectionMapper.selectTrainLitterSection(courseId,vo.getTrainSectionSort());
            vo.setTrainLitterSectionVoList(trainLitterSectionVoList);
        }
        trainCourseVo.setTrainSectionVoList(trainSectionVoList);
        return trainCourseVo;
    }

    @Override
    public TrainSection selectTrainSection(String sectionId) {

        return trainSectionMapper.selectTrainSection(sectionId);
    }

    @Override
    public List<TrainSection> selectSectionList(String trainCourseId, String trainSectionSort) {
        return trainSectionMapper.selectSectionList(trainCourseId, trainSectionSort);
    }

}
