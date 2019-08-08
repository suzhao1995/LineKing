package com.rs.teach.service.training;

import com.rs.teach.mapper.summary.entity.Summary;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-07 12:17
 */
public interface SummaryService {
    /**
     * 课后总结保存
     * @param summary
     */
    void saveSummary(Summary summary);

    void addSummary(Summary summary);

    boolean isEmpty(Summary summary);

    Summary querySummary(Summary summary);
}
