package com.rs.teach.service.training.impl;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.dao.TrainSectionMapper;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.dto.TotleSectionDto;
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

import java.util.ArrayList;
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
    private String fileMappingPath;    //文件存放根目录

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
        Integer result = trainSectionMapper.IsBlank(sectionDto);
        Integer sort = 0;
        if (result > 0){
            TrainLitterSectionVo trainLitterSectionVo = trainSectionMapper.selectTrainLitterSectionSortMax(sectionDto);
            if (StrUtil.isNotBlank(trainLitterSectionVo.getTrainLitterSectionSort())) {
                sort = Integer.valueOf(trainLitterSectionVo.getTrainLitterSectionSort());
            }
        }
        sort += 1;
        sectionDto.setLitterSectionSort(String.valueOf(sort));

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

    @Override
    public void addTotleSection(TotleSectionDto totleSectionDto) {
        Integer result = trainSectionMapper.IsEmpty(totleSectionDto.getCourseId());

        List<TotleSection> list = new ArrayList<>();
        Integer sort = 0;

        if (result > 0){
            //查询当前课程大章节序号最大的一条数据
            TotleSection resultTotleSection = trainSectionMapper.selectTotoleSectionSortMax(totleSectionDto.getCourseId());
            if (StrUtil.isNotBlank(resultTotleSection.getTotleSectionSort())) {
                sort = Integer.valueOf(resultTotleSection.getTotleSectionSort());
            }
        }
        //循环注入大章节序号
        for (int i = 0; i < totleSectionDto.getTotleSectionName().length; i++) {
            sort += 1;
            TotleSection totleSection = new TotleSection();
            totleSection.setCourseId(totleSectionDto.getCourseId());
            totleSection.setTotleSectionSort(String.valueOf(sort));
            totleSection.setTotleSectionName(totleSectionDto.getTotleSectionName()[i]);
            list.add(totleSection);
        }
        //批量插入
        trainSectionMapper.addAllTotleSection(list);
    }

    @Override
    public List<TotleSection> selectTotleSection(TotleSectionDto totleSectionDto) {
        return trainSectionMapper.selectTotleSection(totleSectionDto);
    }

    @Override
    public void updateTotleSection(TotleSectionDto totleSectionDto) {
        totleSectionDto.setTotleSectionNameforUpdate(totleSectionDto.getTotleSectionName()[0]);
        trainSectionMapper.updateTotleSection(totleSectionDto);
    }

}
