package com.rs.teach.controller.backstage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.vo.SchoolVo;
import com.rs.teach.mapper.backstage.vo.TrainDataVo;
import com.rs.teach.service.backstage.SchoolService;
import com.rs.teach.service.backstage.TrainDataService;
import com.rs.teach.service.materiel.MaterielService;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.training.TrainCourseService;
import com.rs.teach.service.video.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-20 17:24
 */
@Slf4j
@RequestMapping("/dataManager")
@Controller
public class DataManageController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private TrainCourseService trainCourseService;
    @Autowired
    private MaterielService materielService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private TrainDataService trainDataService;

    /**
     * @Description: 数据管理
     * @Param: []
     * @return: com.rs.common.utils.ResponseBean
     * @Author: hang_wang
     * @Date: 2019/9/20 17:32
     */
    @RequestMapping(value = "/dataInfo")
    @ResponseBody
    public ResponseBean dataInfo(Integer userPageNum, Integer dataPageNum) {
        ResponseBean bean = new ResponseBean();
        HashMap<String, Object> map = new HashMap<>();

        // TODO 教学课程数量
        Integer courseNum = courseService.selectCourseNum();
        map.put("courseNum", courseNum);

        // TODO 视频课程数量
        Integer videoNum = videoService.selectVideoNum();
        map.put("videoNum", videoNum);

        // TODO 培训课程数量
        Integer trainCourseNum = trainCourseService.selectTrainCourseNum();
        map.put("trainCourseNum", trainCourseNum);

        // TODO 物料资源数量
        Integer MaterielNum = materielService.selectMaterielNum();
        map.put("MaterielNum", MaterielNum);

        // TODO 用户信息
        PageInfo<SchoolVo> userInfo = PageHelper.startPage(userPageNum, 8).doSelectPageInfo(() -> schoolService.selectSchoolVo());
        map.put("userInfo", userInfo);

        // TODO 培训考核
        PageInfo<TrainDataVo> dataInfo = PageHelper.startPage(dataPageNum, 6).doSelectPageInfo(() -> trainDataService.selectTrainDataVo());
        map.put("dataInfo", dataInfo);

        bean.addSuccess(map);
        return bean;
    }

}
