package com.rs.teach.service.training.impl;

import com.rs.teach.mapper.summary.dao.SummaryMapper;
import com.rs.teach.mapper.summary.entity.Summary;
import com.rs.teach.service.training.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-07 12:23
 */
@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private SummaryMapper summaryMapper;

    @Override
    public void saveSummary(Summary summary) {
        summaryMapper.saveSummary(summary);
    }

    @Override
    public void addSummary(Summary summary) {
        summaryMapper.addSummary(summary);
    }

    @Override
    public boolean isEmpty(Summary summary) {
        int result = summaryMapper.isEmpty(summary);
        return result > 0 ? true : false;
    }

    @Override
    public Summary querySummary(Summary summary) {
        return summaryMapper.querySummary(summary);
    }
}
