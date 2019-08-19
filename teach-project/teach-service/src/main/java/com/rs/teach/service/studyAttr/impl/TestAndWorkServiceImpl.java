package com.rs.teach.service.studyAttr.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.studyAttr.dao.TestAndWorkMapper;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.service.studyAttr.TestAndWorkService;

@Service
public class TestAndWorkServiceImpl implements TestAndWorkService {

    @Autowired
    private TestAndWorkMapper mapper;

    @Override
    public Practice getPracticeById(String workId) {
        return mapper.queryPracticeById(workId);
    }

    @Override
    public Testpaper getTestpaper(String testId) {
        return mapper.queryTestpaper(testId);
    }

    @Override
    public void insertPractice(Practice practice) {
        mapper.insertPractice(practice);
    }

    @Override
    public void insertTestpaper(Testpaper testpaper) {
        mapper.insertTestpaper(testpaper);
    }

    @Override
    public void updatePractice(Practice practice) {
        mapper.updatePractice(practice);
    }

    @Override
    public void updateTestpaper(Testpaper testpaper) {
        mapper.updateTestpaper(testpaper);
    }

}