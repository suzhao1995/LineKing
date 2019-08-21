package com.rs.teach.service.training.impl;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.section.dao.TrainSectionMapper;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.section.vo.TrainLitterSectionVo;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.dao.TestAndWorkMapper;
import com.rs.teach.mapper.studyAttr.dao.TrainCourseMapper;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.training.TrainSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private TestAndWorkMapper testAndWorkMapper;

    @Value("${fileMappingPath}")
    private String fileMappingPath;	//文件存放根目录

    @Override
    public TrainCourseVo selectCourseSection(String courseId) {

        TrainCourseVo trainCourseVo = trainCourseMapper.selectTrainCourseById(courseId);
        trainCourseVo.setTrainSectionNumber(trainSectionMapper.selectSectionNum(courseId));

        //查询此课程的大章节
        List<TrainSectionVo> trainSectionVoList = trainSectionMapper.selectTrainSectionById(courseId);
        for (TrainSectionVo vo : trainSectionVoList) {
            //查询小章节
            List<TrainLitterSectionVo> trainLitterSectionVoList = trainSectionMapper.selectTrainLitterSection(courseId, vo.getTrainSectionSort());

            for (TrainLitterSectionVo trainLitterSectionVo : trainLitterSectionVoList) {
                //课件文件全部路径
                String coursewareUrl = fileMappingPath + trainLitterSectionVo.getTrainLitterSectionUrl().replace("/", "\\")
                                        + "\\" + trainLitterSectionVo.getCoursewareId() + "_" + trainLitterSectionVo.getUpdateFileName()
                                        + trainLitterSectionVo.getTrainLitterSectionType();
                trainLitterSectionVo.setCoursewareUrl(coursewareUrl);

                if (StrUtil.isNotEmpty(trainLitterSectionVo.getPracticeId())) {
                    Practice practice = testAndWorkMapper.queryPracticeById(trainLitterSectionVo.getPracticeId());
                    trainLitterSectionVo.setPid(practice.getPid());
                    trainLitterSectionVo.setPracticeFileName(practice.getPracticeFileName());
                    trainLitterSectionVo.setPracticeUrl(practice.getPracticeUrl());
                    trainLitterSectionVo.setPracticePath(practice.getPracticePath());
                }
                if (StrUtil.isNotEmpty(trainLitterSectionVo.getTestpaperId())) {
                    Testpaper testpaper = testAndWorkMapper.queryTestpaper(trainLitterSectionVo.getTestpaperId());
                    trainLitterSectionVo.setTid(testpaper.getTid());
                    trainLitterSectionVo.setTestpaperName(testpaper.getTestpaperName());
                    trainLitterSectionVo.setTestpaperUrl(testpaper.getTestpaperUrl());
                    trainLitterSectionVo.setTestpaperPath(testpaper.getTestpaperPath());
                }
            }
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

    @Override
    public void addTrainSection(SectionDto sectionDto) {
        trainSectionMapper.addTrainSection(sectionDto);
    }

    @Override
    public void updateTrainSection(SectionDto sectionDto) {
        trainSectionMapper.updateTrainSection(sectionDto);
    }

    @Override
    public List<Section> getSectionByCourseId(String courseId) {
        return trainSectionMapper.getSectionByCourseId(courseId);
    }

}
