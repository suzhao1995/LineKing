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

    @Override
    public String queryUrlByPid(String pid) {
        return mapper.queryUrlByPid(pid);
    }

    @Override
    public String queryUrlByTid(String tid) {
        return mapper.queryUrlByTid(tid);
    }

    @Override
    public Practice selectPractice(String sectionId) {
        return mapper.selectPractice(sectionId);
    }

    @Override
    public Testpaper selectTestpaper(String sectionId) {
        return mapper.selectTestpaper(sectionId);
    }

	@Override
	public void delTestPaper(String tid) {
		mapper.delTestPaper(tid);
	}

	@Override
	public void delWork(String pid) {
		mapper.delWork(pid);
	}

}