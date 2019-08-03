package com.rs.teach.service.training.impl;

import com.rs.teach.mapper.section.dao.TrainSectionMapper;
import com.rs.teach.mapper.section.entity.UserCourseRela;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
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

    @Override
    public List<TrainSectionVo> selectAllSection(String courseId) {

        //查询全部课程的章节
        List<TrainSectionVo> list=trainSectionMapper.selectAllSection(courseId);

        return list;
    }

}
