package com.rs.teach.controller.training;

import com.github.pagehelper.PageHelper;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.studyAttr.entity.TrainCourse;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.training.TrainCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 汪航
 * @Description  培训课程
 * @create 2019-08-02 11:27
 */
@Controller
@RequestMapping(value ="/trainCourse")
public class TrainCourseController {

    @Autowired
    private TrainCourseService trainCourseService;

    /**
     * 分页查询培训课程
     * @param trainCourse
     * @return
     */
    @RequestMapping(value = "/pageInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean pageInfo(TrainCourse trainCourse){
        ResponseBean responseBean = new ResponseBean();
        //分页查询
        PageHelper.startPage(trainCourse.getPageNum(),trainCourse.getPageSize());
        List<TrainCourseVo> list = trainCourseService.selectTrainCourse();
        //查询总数
        int resultCount = trainCourseService.count();

        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("total",resultCount);
        responseBean.addSuccess(map);

        return responseBean;
    }



}
