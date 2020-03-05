package com.rs.teach.controller.grade;

import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.grade.dto.GradeDto;
import com.rs.teach.mapper.grade.vo.GradeVo;
import com.rs.teach.service.grade.GradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wanghang
 * @Description  等级测评
 * @create 2020-02-27 0:19
 */
@Slf4j
@Controller
@RequestMapping(value = "/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;


    /**
     * 查询题目(第一题)
     * @return
     */
    @RequestMapping("/questionOne")
    @ResponseBody
    public ResponseBean verifyQuestionOne(@RequestBody GradeDto gradeDto){
        ResponseBean bean = new ResponseBean();

        GradeVo gradeVo = gradeService.selectQuestionOne(gradeDto);
        bean.addSuccess(gradeVo);
        return bean;
    }

    /**
     * 查询题目(下一题)
     * @return
     */
    @RequestMapping("/question")
    @ResponseBody
    public ResponseBean verifyQuestion(@RequestBody GradeDto gradeDto){
        ResponseBean bean = new ResponseBean();

        GradeVo gradeVo = gradeService.selectQuestion(gradeDto);
        bean.addSuccess(gradeVo);
        return bean;
    }

    @RequestMapping("/textToVoice")
    @ResponseBody
    public ResponseBean verifyTextToVoice(){
        ResponseBean bean = new ResponseBean();
        gradeService.textToVoice();
        return bean;
    }

}
