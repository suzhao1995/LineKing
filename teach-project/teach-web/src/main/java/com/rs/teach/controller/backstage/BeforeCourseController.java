package com.rs.teach.controller.backstage;

import cn.hutool.core.util.StrUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.dto.CourseDto;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.training.TrainCourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-14 10:32
 */
@Controller
@RequestMapping("/beforeCourse")
public class BeforeCourseController {

    private static final Logger logger = Logger.getLogger(BeforeCourseController.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    private TrainCourseService trainCourseService;

    /**
     * 添加课程
     *
     * @param courseDto
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addCourse(@RequestParam("file") MultipartFile file, CourseDto courseDto,
                                  HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        //上传文件
        Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
        //文件上传是否成功
        if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
            bean.addError(resultMap.get("message").toString());
            return bean;
        }
        courseDto.setCoursePicUrl(resultMap.get("picUrl").toString());
        try {
            if (StrUtil.equals("1", courseDto.getIsTrain())) {
                trainCourseService.addTrainCourse(courseDto);
            } else {
                courseService.addCourse(courseDto);
            }
            bean.addSuccess("添加成功！");
        } catch (Exception e) {
            logger.error("课程-添加-失败", e);
            bean.addError("添加失败");
        }
        return bean;
    }

    /**
     * 删除课程
     *
     * @param courseDto
     * @return
     */
    @RequestMapping(value = "/deleteCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean deleteCourse(@RequestBody CourseDto courseDto) {
        ResponseBean bean = new ResponseBean();
        String isTrain = courseDto.getIsTrain();
        String courseId = courseDto.getCourseId();
        try {
            if (StrUtil.equals("1", isTrain)) {
                trainCourseService.deleteTrainCourse(courseId);
            } else {
                courseService.deleteCourse(courseId);
            }
            bean.addSuccess("删除成功！");
        } catch (Exception e) {
            logger.error("课程-删除-失败", e);
            bean.addError("删除失败");
        }
        return bean;
    }

    /**
     * 修改课程
     *
     * @param file
     * @param courseDto
     * @param request
     * @return
     */
    @RequestMapping()
    @ResponseBody
    public ResponseBean updateCourse(@RequestParam("file") MultipartFile file, CourseDto courseDto,
                                     HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();

        //上传文件
        Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
        //文件上传是否成功
        if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
            bean.addError(resultMap.get("message").toString());
            return bean;
        }
        courseDto.setCoursePicUrl(resultMap.get("picUrl").toString());
        try {
            if (StrUtil.equals("1", courseDto.getIsTrain())) {
                trainCourseService.updateTrainCourse(courseDto);
            } else {
                courseService.updateCourse(courseDto);
            }
            bean.addSuccess("修改成功！");
        } catch (Exception e) {
            logger.error("课程-修改-失败", e);
            bean.addError("修改失败");
        }
        return bean;
    }

    /**
     * 查询课程
     *
     * @param courseDto
     * @return
     */
    @RequestMapping(value = "/queryCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean queryCourse(@RequestBody CourseDto courseDto) {
        ResponseBean bean = new ResponseBean();
        try {
            if (StrUtil.equals("1", courseDto.getIsTrain())) {
                List<TrainCourseVo> courseVos = trainCourseService.selectTrainCourse();
                bean.addSuccess(courseVos);
            } else {
                List<Course> courseVos = courseService.selectCourse();
                bean.addSuccess(courseVos);
            }
        } catch (Exception e) {
            logger.error("课程-查询-失败", e);
            bean.addError("查询失败");
        }
        return bean;
    }

}
