package com.rs.teach.service.training.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.mapper.section.dao.TrainSectionMapper;
import com.rs.teach.mapper.studyAttr.dao.TrainCourseMapper;
import com.rs.teach.mapper.studyAttr.dto.CourseDto;
import com.rs.teach.mapper.studyAttr.vo.CourseAllUrl;
import com.rs.teach.mapper.studyAttr.vo.CourseVo;
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

    @Autowired
    private TrainSectionMapper trainSectionMapper;

    @Override
    public List<TrainCourseVo> selectTrainCourse(CourseDto courseDto) {

        List<TrainCourseVo> trainCourseVos = trainCourseMapper.selectTrainCourse(courseDto);
        for (TrainCourseVo vo : trainCourseVos) {
            vo.setTrainSectionNumber(trainSectionMapper.selectSectionNum(vo.getTrainCourseId()));
        }
        return trainCourseVos;
    }

    @Override
    public void addTrainCourse(CourseDto courseDto) {
        trainCourseMapper.addTrainCourse(courseDto);
    }

    @Override
    public void deleteTrainCourse(String courseId) {
        trainCourseMapper.deleteTrainCourse(courseId);
    }

    @Override
    public void updateTrainCourse(CourseDto courseDto) {
        trainCourseMapper.updateTrainCourse(courseDto);
    }

    @Override
    public TrainCourseVo selectTrainCourseById(String courseId) {
        return trainCourseMapper.selectTrainCourseById(courseId);
    }

    @Override
    public List<OptionVo> trainCourseList() {
        return trainCourseMapper.trainCourseList();
    }

    @Override
    public CourseVo echoCourse(CourseDto courseDto) {

        return trainCourseMapper.echoCourse(courseDto.getCourseId());
    }

    @Override
    public Integer selectTrainCourseNum() {
        return trainCourseMapper.selectTrainCourseNum();
    }

    @Override
    public boolean isEmptyFile(String courseId) {

        List<CourseAllUrl> list = trainCourseMapper.isEmptyFile(courseId);
        if (CollectionUtil.isEmpty(list)){
            return false;
        }
        for (CourseAllUrl vo : list) {
            if (StrUtil.isNotEmpty(vo.getCourseUrl())){
                return true;
            }
            if (StrUtil.isNotEmpty(vo.getPracticeUrl())){
                return true;
            }
            if (StrUtil.isNotEmpty(vo.getTestpaperUrl())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmptyFileBySection(String sectionId) {
        Integer count = trainCourseMapper.isEmptyFileBySection(sectionId);
        if(count >= 1){
            return false;
        }else {
            return true;
        }
    }

}
