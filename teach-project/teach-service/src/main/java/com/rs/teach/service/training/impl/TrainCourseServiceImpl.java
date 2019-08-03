package com.rs.teach.service.training.impl;

import com.rs.teach.mapper.studyAttr.dao.TrainCourseMapper;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.training.TrainCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 13:02
 */
@Service
public class TrainCourseServiceImpl implements TrainCourseService {

    @Autowired
    private TrainCourseMapper trainCourseMapper;

    @Override
    public List<TrainCourseVo> selectTrainCourse() {
        return trainCourseMapper.selectTrainCourse();
    }

    @Override
    public int count() {
        return trainCourseMapper.count();
    }
}
