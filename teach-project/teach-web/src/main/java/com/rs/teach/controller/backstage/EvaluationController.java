package com.rs.teach.controller.backstage;

import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.entity.Evaluation;
import com.rs.teach.mapper.common.Enums.EvaluationGradeEnum;
import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.service.backstage.EvaluationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-05 17:06
 */
@Slf4j
@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    /**
     * 考核评价数据回显
     *
     * @param evaluation
     * @return
     */
    @RequestMapping(value = "/selectEvaluationById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectEvaluationById(@RequestBody Evaluation evaluation) {
        ResponseBean bean = new ResponseBean();
        Evaluation vo = evaluationService.selectEvaluationById(evaluation.getEvaluationId());
        bean.addSuccess(vo);
        return bean;
    }


    /**
     * 考核评价数据修改
     *
     * @param evaluation
     * @return
     */
    @RequestMapping(value = "/updateEvaluationById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateEvaluationById(@RequestBody Evaluation evaluation) {
        ResponseBean bean = new ResponseBean();
        evaluationService.updateEvaluationById(evaluation);
        bean.addSuccess("成功");
        return bean;
    }

    /**
     * 考核成绩下拉列表
     *
     * @return
     */
    @RequestMapping("/queryGrade")
    @ResponseBody
    public ResponseBean queryGrade() {
        ResponseBean bean = new ResponseBean();
        List<OptionVo> list = EvaluationGradeEnum.condition();
        bean.addSuccess(list);
        return bean;
    }
}
