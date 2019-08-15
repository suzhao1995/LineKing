package com.rs.teach.service.training;

import com.rs.teach.mapper.summary.entity.Summary;

/**
 * @author 汪航
 * @Description  课后总结service
 * @create 2019-08-07 12:17
 */
public interface SummaryService {
    /**
     * 课后总结保存
     * 存在课后总结则修改，不存在则新增
     * @param summary
     */
    void saveSummary(Summary summary);

    /**
     *  查询课后总结
     * @param summary
     * @return
     */
    Summary querySummary(Summary summary);
}
