package com.rs.teach.controller.training;

import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.summary.entity.Summary;
import com.rs.teach.service.training.SummaryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author 汪航
 * @Description
 * @create 2019-08-07 12:08
 */
@Controller
@RequestMapping("/summary")
public class SummaryController {

    private final static Logger logger = Logger.getLogger(SummaryController.class);
    @Autowired
    private SummaryService summaryService;

    /**
     * 课后总结保存
     * param
     * userId; //用户id
     * sectionSort;  //大章节序号
     * courseId      //课程id
     * summary      //课后总结
     */
    @RequestMapping(value = "saveSummary", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean saveSummary(@RequestBody Summary summary, String sessionKey) {
        ResponseBean responseBean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
        summary.setUserId(userId);
        try {
            boolean flag = summaryService.isEmpty(summary);
            if (!flag) {
                summaryService.addSummary(summary);
            }
            summaryService.saveSummary(summary);
            responseBean.addSuccess();
        } catch (Exception e) {
            logger.error("课后总结：保存失败", e);
            responseBean.addError("保存失败，系统异常");
        }
        return responseBean;
    }

    /**
     * 课后总结数据回显
     * param
     * userId; //用户id
     * sectionSort;  //大章节序号
     * courseId      //课程id
     */
    @RequestMapping(value = "querySummary", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean querySummary(@RequestBody Summary summary, String sessionKey) {
        ResponseBean responseBean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
        summary.setUserId(userId);
        Summary resultSummary = summaryService.querySummary(summary);
        responseBean.addSuccess(resultSummary);
        return responseBean;
    }
}
