package com.rs.teach.service.backstage.impl;

import com.rs.teach.mapper.backstage.dao.EvaluationMapper;
import com.rs.teach.mapper.backstage.entity.Evaluation;
import com.rs.teach.service.backstage.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 17:07
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    @Autowired
    private EvaluationMapper evaluationMapper;


    @Override
    public Evaluation selectEvaluationById(String evaluationId) {
        return evaluationMapper.selectEvaluationById(evaluationId);
    }

    @Override
    public void updateEvaluationById(Evaluation evaluation) {
        evaluationMapper.updateEvaluationById(evaluation);
    }
}
