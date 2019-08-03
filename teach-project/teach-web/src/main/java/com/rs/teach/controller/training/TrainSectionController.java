package com.rs.teach.controller.training;

import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.StrUtil;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.section.entity.UserCourseRela;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.service.training.TrainSectionService;
import com.rs.teach.service.training.UserCourseRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 16:39
 */
@Controller
@RequestMapping("/trainSection")
public class TrainSectionController {

    @Autowired
    private TrainSectionService trainSectionService;

    @Autowired
    private UserCourseRelaService userCourseRelaService;

    /**
     * 查询所有的课程章节
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "selectSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectSection(HttpServletRequest request) {
        ResponseBean responseBean = new ResponseBean();
        HashMap<String, Object> map = new HashMap<>();

        String sessionKey = request.getParameter("sessionKey");
        String courseId = request.getParameter("courseId");
        if (StrUtil.isEmpty(sessionKey)) {
            responseBean.addError("请先登录");
            return responseBean;
        }
        if (StrUtil.isEmpty(courseId)) {
            responseBean.addError("没有课程Id");
            return responseBean;
        }
        try {
            String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
            System.out.println("userId = " + userId);
            //查询全部章节
            List<TrainSectionVo> sections = trainSectionService.selectAllSection(courseId);
            map.put("sections", sections);

            //查询章节学习状态
            List<UserCourseRela> studyStatus = userCourseRelaService.studyStatus(courseId, userId);
            map.put("studyStatus", studyStatus);

            responseBean.addSuccess(map);
            return responseBean;
        } catch (Exception e) {
            responseBean.addError("用户未登录");
            return responseBean;
        }
    }

    /**
     * 加入我的课程
     */
    @RequestMapping(value = "join", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean join() {
        ResponseBean responseBean = new ResponseBean();
        try {

        } catch (Exception e) {

        }
        return responseBean;
    }

    /**
     * 取消我的课程
     */
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean cancel() {
        ResponseBean responseBean = new ResponseBean();
        return responseBean;
    }

    /**
     * 在线查看
     */
    @RequestMapping(value = "seeOnline", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean seeOnline() {
        ResponseBean responseBean = new ResponseBean();
        return responseBean;
    }

    /**
     * 资源下载
     */
    @RequestMapping(value = "download", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean download() {
        ResponseBean responseBean = new ResponseBean();
        return responseBean;
    }

    /**
     * 课件内容资源下载
     */
    @RequestMapping(value = "downloadAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean downloadAll() {
        ResponseBean responseBean = new ResponseBean();
        return responseBean;
    }


}
