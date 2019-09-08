package com.rs.teach.service.backstage.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.rs.teach.mapper.backstage.dao.AnswerSheetMapper;
import com.rs.teach.mapper.backstage.dao.EvaluationMapper;
import com.rs.teach.mapper.backstage.dao.UserTrainDataRelaMapper;
import com.rs.teach.mapper.backstage.entity.AnswerSheet;
import com.rs.teach.mapper.backstage.entity.Evaluation;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.mapper.backstage.vo.AnswerSheetVo;
import com.rs.teach.mapper.common.Enums.EvaluationGradeEnum;
import com.rs.teach.service.backstage.AnswerSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 12:33
 */
@Service
public class AnswerSheetServiceImpl implements AnswerSheetService {
    @Autowired
    private AnswerSheetMapper answerSheetMapper;
    @Autowired
    private UserTrainDataRelaMapper userTrainDataRelaMapper;
    @Autowired
    private EvaluationMapper evaluationMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addAnswerSheet(AnswerSheet answerSheet, String id) {
        try {
            //添加考核人员上传答卷表
            answerSheetMapper.addAnswerSheet(answerSheet);
            //添加评价表
            Evaluation evaluation = new Evaluation();
            evaluation.setEvaluationId(answerSheet.getEvaluationId());
            evaluation.setGrade(EvaluationGradeEnum.PENDING_REVIEW.getLabel());
            evaluationMapper.addEvaluation(evaluation);

            String answerSheetId = answerSheetMapper.selectAnswerSheetId(answerSheet);
            //添加关联表（USER_TRAIN_DATA_RELA）考核人员上传答卷表id
            UserTrainDataRela userTrainDataRela = new UserTrainDataRela();
            userTrainDataRela.setDataId(id);
            userTrainDataRela.setTrainCourseId(answerSheet.getTrainCourseId());
            userTrainDataRela.setUserId(answerSheet.getUserId());
            userTrainDataRela.setAnswerSheetId(answerSheetId);
            userTrainDataRelaMapper.updateUserTrainData(userTrainDataRela);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public AnswerSheet selectAnswerSheet(String answerSheetId) {
        return answerSheetMapper.selectAnswerSheet(answerSheetId);
    }

    @Override
    public List<AnswerSheetVo> queryAnswerSheet(String id) {
        return answerSheetMapper.queryAnswerSheet(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAnswerSheetById(String answerSheetId) {
        try {
            answerSheetMapper.deleteAnswerSheetById(answerSheetId);
            evaluationMapper.selectEvaluationId(answerSheetId);
        } catch (Exception e) {
            throw e;
        }

    }


}
