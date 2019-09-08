package com.rs.teach.mapper.backstage.dao;

import com.rs.teach.mapper.backstage.entity.Evaluation;
import org.apache.ibatis.annotations.Param;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 16:40
 */
public interface EvaluationMapper {
    /**
     * 添加评价
     * @param evaluation
     */
    void addEvaluation(Evaluation evaluation);

    /**
     * 数据回显
     * @param evaluationId
     * @return
     */
    Evaluation selectEvaluationById(@Param("evaluationId") String evaluationId);

    /**
     * 修改评价表
     * @param evaluation
     */
    void updateEvaluationById(Evaluation evaluation);

    /**
     * 删除评价表
     * @param answerSheetId
     */
    void selectEvaluationId(@Param("answerSheetId") String answerSheetId);
}
