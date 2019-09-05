package com.rs.teach.service.backstage;

import com.rs.teach.mapper.backstage.entity.Evaluation;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 17:07
 */
public interface EvaluationService {
    /**
     * 数据回显
     * @param evaluationId
     * @return
     */
    Evaluation selectEvaluationById(String evaluationId);

    /**
     * 修改评价表
     * @param evaluation
     */
    void updateEvaluationById(Evaluation evaluation);
}
