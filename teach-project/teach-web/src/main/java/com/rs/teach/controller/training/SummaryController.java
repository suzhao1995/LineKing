package com.rs.teach.controller.training;

import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.summary.entity.Summary;
import com.rs.teach.service.training.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-07 12:08
 */
@Controller
@RequestMapping("/summary")
public class SummaryController {

    @Autowired
    private SummaryService summaryService;

    /**
     * 课后总结保存
     * param
     *     userId; //用户id
     *     sectionSort;  //大章节序号
     *     courseId      //课程id
     *     summary      //课后总结
     */
    @RequestMapping(value = "saveSummary", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean saveSummary(Summary summary, String sessionKey) {
        ResponseBean responseBean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
        summary.setUserId(userId);
        summaryService.saveSummary(summary);
        return responseBean;
    }

}
