package com.rs.teach.mapper.summary.dao;

import com.rs.teach.mapper.summary.entity.Summary;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-07 12:43
 */
public interface SummaryMapper {

    /**
     * 修改课后总结
     * @param summary
     * @return
     */
    int updateSummary(Summary summary);
    /**
     * 添加课后总结
     * @param summary
     * @return
     */
    void addSummary(Summary summary);
    /**
     * 课后总结保存
     * 存在课后总结则修改，不存在则新增
     * @param summary
     */
    int isEmpty(Summary summary);
    /**
     *  查询课后总结
     * @param summary
     * @return
     */
    Summary querySummary(Summary summary);
}
