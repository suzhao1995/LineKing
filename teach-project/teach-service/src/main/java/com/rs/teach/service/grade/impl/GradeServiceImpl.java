package com.rs.teach.service.grade.impl;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.grade.dao.GradePreschoolMapper;
import com.rs.teach.mapper.grade.dto.GradeDto;
import com.rs.teach.mapper.grade.entity.GradePreschool;
import com.rs.teach.mapper.grade.vo.GradeVo;
import com.rs.teach.service.grade.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2020-02-27 0:20
 */
@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradePreschoolMapper gradePreschoolMapper;

    @Transactional
    @Override
    public GradeVo selectQuestion(GradeDto gradeDto) {
        GradeVo gradeVo = new GradeVo();
        gradePreschoolMapper.addSheet(gradeDto);
        if (gradeDto.getSort() != 10) {
            //查询下一条数据
            gradeDto.setSort(gradeDto.getSort() + 1);
            gradeVo = gradePreschoolMapper.selectQuestion(gradeDto);
            gradeVo.setList(gradePreschoolMapper.selectAnswers(gradeVo.getPreschoolId()));
        } else if (gradeDto.getSort() == 10) {
            //查询正确题目数量
            Integer count = gradePreschoolMapper.selectCount(gradeDto.getBabyId());
            if (count == 0) {
                gradeVo.setAcc("5%");
            } else if (count == 10) {
                gradeVo.setAcc("99%");
            } else {
                gradeVo.setAcc(StrUtil.format("{}%", count * 10));
            }
        }

        return gradeVo;
    }

    @Override
    public GradeVo selectQuestionOne(GradeDto gradeDto) {
        //添加baby账号
        Integer count = gradePreschoolMapper.addBaby(gradeDto);

        //查询下一条数据
        gradeDto.setSort(1);
        GradeVo gradeVo = gradePreschoolMapper.selectQuestion(gradeDto);
        gradeVo.setList(gradePreschoolMapper.selectAnswers(gradeVo.getPreschoolId()));
        gradeVo.setBabyId(gradeDto.getBabyId());
        return gradeVo;
    }

    @Override
    public void textToVoice() {
        List<GradePreschool> list = gradePreschoolMapper.selectAll();
        List<GradePreschool> gradePreschools = TextToVoice.questionAudio(list);
        //gradePreschoolMapper.addQuestionAudio(gradePreschools);
        for (GradePreschool gradePreschool : gradePreschools) {
            gradePreschoolMapper.addAudio(gradePreschool);
        }
    }
}
