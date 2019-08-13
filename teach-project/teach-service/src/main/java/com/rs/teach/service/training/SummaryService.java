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
     * 存在笔记则修改，不存在则新增
     * @param summary
     */
    void saveSummary(Summary summary);

    Summary querySummary(Summary summary);
}
