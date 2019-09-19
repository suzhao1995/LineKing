package com.rs.teach.service.section.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.backstage.entity.TotleSection;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.section.dto.TotleSectionDto;
import com.rs.teach.mapper.section.vo.TrainLitterSectionVo;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.dao.CourseMapper;
import com.rs.teach.mapper.studyAttr.dao.TestAndWorkMapper;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.section.dao.SectionMapper;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.service.section.SectionService;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionMapper mapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TestAndWorkMapper testAndWorkMapper;

    @Value("${fileMappingPath}")
    private String fileMappingPath;    //文件存放根目录

    @Override
    public List<Section> getSectionByUser(String userId, String sectionId) {
        return mapper.querySectionByUser(userId, sectionId);
    }

    @Override
    public Section getSectionById(String sectionId) {
        return mapper.querySection(sectionId);
    }

    @Override
    public int addTeachUpSection(Section section) {
        return mapper.insertTeachUpSection(section);
    }

    @Override
    public Section getUpLoadSection(String upLoadId) {
        return mapper.queryUpLoadSection(upLoadId);
    }

    @Override
    public List<Section> getSectionByCourseId(String courseId) {
        return mapper.querySectionByCourseId(courseId);
    }

    @Override
    public List<Map<String, Object>> getSectionStatus(String courseId, String userId, String classId) {
        return mapper.querySectionStatus(courseId, userId, classId);
    }

    @Override
    public void addSection(SectionDto sectionDto) {
        Integer result = mapper.IsBlank(sectionDto);
        Integer sort = 0;
        if (result > 0){
            TrainLitterSectionVo trainLitterSectionVo = mapper.selectTrainLitterSectionSortMax(sectionDto);
            if (StrUtil.isNotBlank(trainLitterSectionVo.getTrainLitterSectionSort())) {
                sort = Integer.valueOf(trainLitterSectionVo.getTrainLitterSectionSort());
            }
        }
        sort += 1;
        sectionDto.setLitterSectionSort(String.valueOf(sort));

        mapper.addSection(sectionDto);
    }

    @Override
    public TrainCourseVo selectCourseSection(String courseId) {
        TrainCourseVo trainCourseVo = courseMapper.selectCourseById(courseId);
        trainCourseVo.setTrainSectionNumber(Integer.valueOf(mapper.selectSectionNum(courseId)));

        //查询此课程的大章节
        List<TrainSectionVo> trainSectionVoList = mapper.selectSectionById(courseId);
        for (TrainSectionVo vo : trainSectionVoList) {
            //查询小章节
            List<TrainLitterSectionVo> litterSectionVoList = mapper.selectLitterSection(courseId, vo.getId());

            for (TrainLitterSectionVo litterSectionVo : litterSectionVoList) {
                if (StrUtil.isNotEmpty(litterSectionVo.getTrainLitterSectionUrl())) {
                    //课件文件全部路径
                    String coursewareUrl = fileMappingPath + litterSectionVo.getTrainLitterSectionUrl().replace("/", "\\")
                            + "\\" + litterSectionVo.getCoursewareId() + "_" + litterSectionVo.getUpdateFileName()
                            + litterSectionVo.getTrainLitterSectionType();
                    litterSectionVo.setCoursewareUrl(coursewareUrl);
                }
                if (StrUtil.isNotEmpty(litterSectionVo.getPracticeId())) {
                    Practice practice = testAndWorkMapper.queryPracticeById(litterSectionVo.getPracticeId());
                    litterSectionVo.setPid(practice.getPid());
                    litterSectionVo.setPracticeFileName(practice.getPracticeFileName());
                    litterSectionVo.setPracticeUrl(practice.getPracticeUrl());
                    litterSectionVo.setPracticePath(practice.getPracticePath());
                }
                if (StrUtil.isNotEmpty(litterSectionVo.getTestpaperId())) {
                    Testpaper testpaper = testAndWorkMapper.queryTestpaper(litterSectionVo.getTestpaperId());
                    litterSectionVo.setTid(testpaper.getTid());
                    litterSectionVo.setTestpaperName(testpaper.getTestpaperName());
                    litterSectionVo.setTestpaperUrl(testpaper.getTestpaperUrl());
                    litterSectionVo.setTestpaperPath(testpaper.getTestpaperPath());
                }
            }
            vo.setTrainLitterSectionVoList(litterSectionVoList);
        }
        trainCourseVo.setTrainSectionVoList(trainSectionVoList);
        return trainCourseVo;
    }

    @Override
    public void updateSection(SectionDto sectionDto) {
        mapper.updateSection(sectionDto);
    }

    @Override
    public void addTotleSection(TotleSectionDto totleSectionDto) {
        Integer result = mapper.IsEmpty(totleSectionDto.getCourseId());
        List<TotleSection> list = new ArrayList<>();
        Integer sort = 0;

        if (result > 0) {
            //查询当前课程大章节序号最大的一条数据
            TotleSection resultTotleSection = mapper.selectTotoleSectionSortMax(totleSectionDto.getCourseId());
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
        mapper.addAllTotleSection(list);
    }

    @Override
    public List<TotleSection> selectTotleSection(TotleSectionDto totleSectionDto) {
        return mapper.selectTotleSection(totleSectionDto);
    }

    @Override
    public void updateTotleSection(TotleSectionDto totleSectionDto) {
        totleSectionDto.setTotleSectionNameforUpdate(totleSectionDto.getTotleSectionName()[0]);
        mapper.updateTotleSection(totleSectionDto);
    }
}