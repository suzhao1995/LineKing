package com.rs.teach.controller.training;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.common.PageDto;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.training.TrainCourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 汪航
 * @Description  培训课程
 * @create 2019-08-02 11:27
 */
@Controller
@RequestMapping(value ="/trainCourse")
public class TrainCourseController {

    private final static Logger logger = Logger.getLogger(TrainCourseController.class);

    @Autowired
    private TrainCourseService trainCourseService;

    /**
     * 分页查询培训课程
     * @param pageDto
     * @return
     */
    @RequestMapping(value = "/pageInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean pageInfo(@RequestBody PageDto pageDto){
        ResponseBean responseBean = new ResponseBean();
        try {
            //分页查询
            PageInfo<TrainCourseVo> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() -> trainCourseService.selectTrainCourse());
            responseBean.addSuccess(pageInfo);
            return responseBean;
        }catch (Exception  e){
            logger.debug("培训课程-培训课程列表-查询失败",e);
            responseBean.addError("查询失败");
            return responseBean;
        }
    }

    /**
     * 课件内容全部下载（当前课程）
     */
    @RequestMapping(value = "downloadAll", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean downloadAll(@RequestBody String courseId) {
        ResponseBean responseBean = new ResponseBean();
        try {

        }catch (Exception e){
            logger.error("当前课程-下载失败",e);
            responseBean.addError("下载失败");
        }
        return responseBean;
    }



}
