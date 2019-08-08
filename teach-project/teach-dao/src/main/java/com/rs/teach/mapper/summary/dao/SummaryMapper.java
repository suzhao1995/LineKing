package com.rs.teach.mapper.summary.dao;

import com.rs.teach.mapper.summary.entity.Summary;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-07 12:43
 */
public interface SummaryMapper {

    int saveSummary(Summary summary);

    void addSummary(Summary summary);
}
