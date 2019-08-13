package com.rs.teach.controller.training;

import cn.hutool.core.util.StrUtil;
import com.rs.common.utils.BPUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.common.Enums.CourseStatusEnum;
import com.rs.teach.mapper.common.Enums.RelaTypeEnum;
import com.rs.teach.mapper.common.TrainParamDto;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.training.TrainSectionService;
import com.rs.teach.service.training.UserCourseRelaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private final static Logger logger = Logger.getLogger(TrainSectionController.class);
    /**
     * 章节sevice
     */
    @Autowired
    private TrainSectionService trainSectionService;

    /**
     * 用户章节关联service
     */
    @Autowired
    private UserCourseRelaService userCourseRelaService;

    /**
     * 查询所有的课程章节
     *
     * @param trainParamDto
     * @return
     */
    @RequestMapping(value = "selectSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectSection(String sessionKey, @RequestBody TrainParamDto trainParamDto) {
        ResponseBean responseBean = new ResponseBean();
        HashMap<String, Object> map = new HashMap<>();
        String courseId = trainParamDto.getCourseId();
        try {
            //参数效验
            BPUtil.check(StrUtil.isEmpty(courseId), "没有课程ID");
            String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();

            //查询课程章节信息
            TrainCourseVo trainCourseVo = trainSectionService.selectCourseSection(courseId);
            map.put("trainCourseVo", trainCourseVo);

            //判断是否加入我的课程
            Integer status = userCourseRelaService.studyStatus(userId, courseId);
            map.put("status", status);

            responseBean.addSuccess(map);
            return responseBean;
        } catch (Exception e) {
            logger.debug("培训-查询所有的课程章节-失败", e);
            responseBean.addError(e.getMessage());
            return responseBean;
        }
    }

    /**
     * 加入我的课程
     */
    @RequestMapping(value = "join", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean join(String sessionKey, @RequestBody TrainParamDto trainParamDto) {
        ResponseBean responseBean = new ResponseBean();
        String courseId = trainParamDto.getCourseId();
        try {

            String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
            //加入到我的课程
            userCourseRelaService.join(courseId, userId);
            return responseBean;
        } catch (Exception e) {
            logger.debug("培训-加入我的课程-失败", e);
            responseBean.addError(e.getMessage());
            return responseBean;
        }
    }

    /**
     * 取消我的课程
     */
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean cancel(String sessionKey, @RequestBody TrainParamDto trainParamDto) {
        ResponseBean responseBean = new ResponseBean();
        String courseId = trainParamDto.getCourseId();
        try {
            //参数效验
            BPUtil.check(StrUtil.isEmpty(courseId), "没有课程ID");

            String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
            //取消到我的课程
            userCourseRelaService.cancel(userId, courseId);
            return responseBean;
        } catch (Exception e) {
            logger.debug("培训-取消我的课程-失败", e);
            responseBean.addError(e.getMessage());
            return responseBean;
        }
    }

    /**
     * 在线查看
     */
    @RequestMapping(value = "seeOnline", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean seeOnline(String sessionKey, @RequestBody TrainParamDto trainParamDto) {
        ResponseBean responseBean = new ResponseBean();
        String sectionId = trainParamDto.getSectionId();
        try {
            //参数效验

            BPUtil.check(StrUtil.isEmpty(sectionId), "没有章节Id");
            String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();

            HashMap<String, Object> map = new HashMap<>();

            //查询返回页面信息（1.当前小章节全部信息)
            TrainSection trainSection = trainSectionService.selectTrainSection(sectionId);
            map.put("trainSection", trainSection);

            //查询小章节目录
            List<TrainSection> sectionList = trainSectionService.selectSectionList(trainSection.getTrainCourseId(), trainSection.getTrainSectionSort());
            map.put("sectionList", sectionList);

            //判断标识符
            Integer status = userCourseRelaService.studyStatus(userId, trainSection.getTrainCourseId());
            map.put("status", status);

            //加入到我的课程就修改学习状态
            if (status == RelaTypeEnum.convent2TableNum(RelaTypeEnum.JOIN.name())) {
                userCourseRelaService.updateIsFinish(trainSection.getTrainCourseId(),userId,sectionId, CourseStatusEnum.convent2TableNum(CourseStatusEnum.STARTING.name()));
            }

            //查询返回页面信息（pdf图片文件）


            responseBean.addSuccess(map);
            return responseBean;
        } catch (Exception e) {
            logger.debug("培训-在线查看-失败", e);
            responseBean.addError(e.getMessage());
            return responseBean;
        }
    }


    /**
     * 资源下载（当前小章节）
     * trainSectionId  文件上传保存的唯一id
     */
    @RequestMapping(value = "download", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean download() {
        ResponseBean responseBean = new ResponseBean();
        try {

        } catch (Exception e) {
            logger.error("当前小章节-下载失败", e);
            responseBean.addError("下载失败");
        }
        return responseBean;
    }

}
