package com.rs.teach.controller.backstage;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.mapper.common.PageDto;
import com.rs.teach.mapper.studyAttr.dto.CourseDto;
import com.rs.teach.mapper.studyAttr.entity.Course;
import com.rs.teach.mapper.studyAttr.vo.CourseVo;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.mapper.sysCode.entity.SysCode;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.sysCode.SysCodeService;
import com.rs.teach.service.training.TrainCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
 * @Description   管理员 -- 课程模块
 * @create 2019-08-14 10:32
 */
@Controller
@RequestMapping("/beforeCourse")
@Api(value = "/beforeCourse",tags = "BeforeCourseController",description = "管理员 -- 课程模块")
public class BeforeCourseController {

    private static final Logger logger = Logger.getLogger(BeforeCourseController.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    private TrainCourseService trainCourseService;

    @Autowired
    private SysCodeService sysCodeService;

    /**
     * 管理员---初始化课程分类列表
     * @return ResponseBean
     * @date 2019年8月17日 下午1:14:05
     */
    @RequestMapping(value = "/initCourseType",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean initCourseType(@RequestBody PageDto pageDto){
        ResponseBean bean = new ResponseBean();
        //初始化视频分类
        PageInfo<SysCode> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() ->sysCodeService.getSysCodeList("COURSE_CODE"));
        bean.addSuccess(pageInfo);
        return bean;
    }

    /**
     * 管理员---删除视频分类
     * @return ResponseBean
     * @author suzhao
     * @date 2019年8月17日 下午1:14:05
     */
    @RequestMapping("/delCourseType")
    @ResponseBody
    public ResponseBean delCourseType(@RequestParam("cid") String cid){
        ResponseBean bean = new ResponseBean();

        //判断该视频分类是否有视频
        SysCode sysCode = sysCodeService.getSysCode(cid);
        if(sysCode != null){
            List<Course> list = courseService.adminGetVideos(sysCode.getCodeValue());
            if(list != null && list.size() > 0){
                bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"该课程分类中含有课程课件，请先删除课程课件");
                return bean;
            }
        }
        //删除课程分类
        sysCodeService.delSysCode(cid);
        bean.addSuccess();
        return bean;
    }


    /**
     * 添加课程分类
     * @param sysCode
     * @return
     */
    @RequestMapping(value = "/addCourseType", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addCourseType(@RequestBody SysCode sysCode, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        //查询物料分类总数 限定9个
        List<SysCode> list = sysCodeService.getSysCodeList("COURSE_CODE");
        sysCode.setCid(DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN));
        sysCode.setCodeType("COURSE_CODE");
        sysCode.setCreateBy(userId);
        sysCode.setCreateDate(DateUtil.format(DateTime.now(),DatePattern.NORM_DATE_PATTERN));
        if(list.size() > 0){
            SysCode lastCode = list.get(list.size() - 1);	//获取list集合最后一个对象
            int code = Integer.valueOf(lastCode.getCode()) + 1;
            int sort = Integer.valueOf(lastCode.getCodeSort()) + 10;
            sysCode.setCode(String.valueOf(code));
            sysCode.setCodeSort(String.valueOf(sort));
        }else{
            sysCode.setCode("3001");
            sysCode.setCodeSort("1010");
        }
        try {
            sysCodeService.addSysCode(sysCode);
            bean.addSuccess("添加成功！");
        } catch (Exception e) {
            logger.error("课程分类-添加-失败", e);
            bean.addError("添加失败");
        }
        return bean;
    }

    /**
     * 管理员---修改课程分类
     * @param
     * @throws
     * @return ResponseBean
     * @author suzhao
     * @date 2019年8月17日 下午1:14:05
     */
    @RequestMapping("/updateCourseType")
    @ResponseBody
    public ResponseBean updateCourseType(HttpServletRequest request, @RequestBody SysCode sysCode){
        ResponseBean bean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();

        sysCode.setCodeType("COURSE_CODE");
        sysCode.setCreateBy(userId);
        sysCode.setCreateDate(DateUtil.format(DateTime.now(),DatePattern.PURE_DATE_PATTERN));

        try {
            int result = sysCodeService.modifySysCode(sysCode);
            if(result == 0){
                bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"修改课程分类失败");
                return bean;
            }
            bean.addSuccess();
        } catch (Exception e) {
            bean.addError("系统异常");
            logger.info("---------修改课程分类失败，系统异常--------", e);
        }

        return bean;
    }


    /**
     * 课程类型下拉列表
     *
     * @return
     */
    @RequestMapping(value = "/courseType")
    @ResponseBody
    public ResponseBean courseType() {
        ResponseBean bean = new ResponseBean();
        List<OptionVo> list = courseService.courseType();
        bean.addSuccess(list);
        return bean;
    }

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
        //上传图片
        Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
        //文件上传是否成功
        if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
            bean.addError(ResponseBean.CODE_PICTURE_ERROR,resultMap.get("message").toString());
            return bean;
        }
        courseDto.setCoursePicUrl(resultMap.get("picUrl").toString());
        try {
            if (StrUtil.equals("1", courseDto.getIsTrain())) {
                courseDto.setAddTime(DateUtil.now());
                trainCourseService.addTrainCourse(courseDto);
            } else if(StrUtil.equals("0", courseDto.getIsTrain())){
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
            } else if(StrUtil.equals("0", courseDto.getIsTrain())){
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
     * 修改课程回显
     *
     * @param courseDto
     * @return
     */
    @RequestMapping(value = "/echoCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean echoCourse(@RequestBody CourseDto courseDto) {
        ResponseBean bean = new ResponseBean();
        CourseVo vo = null;
        try {
            if (StrUtil.equals("1", courseDto.getIsTrain())) {
                vo = trainCourseService.echoCourse(courseDto);
            } else if(StrUtil.equals("0", courseDto.getIsTrain())) {
                vo = courseService.echoCourse(courseDto);
            }
            bean.addSuccess(vo);
        } catch (Exception e) {
            logger.error("课程-回显-失败", e);
            bean.addError("回显失败");
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
    @RequestMapping(value = "/updateCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateCourse(@RequestParam(value = "file",required = false) MultipartFile file, CourseDto courseDto,
                                     HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        if(file != null) {
            if (!file.isEmpty()) {
                //上传文件
                Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
                //文件上传是否成功
                if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
                    bean.addError(ResponseBean.CODE_PICTURE_ERROR, resultMap.get("message").toString());
                    return bean;
                }
                courseDto.setCoursePicUrl(resultMap.get("picUrl").toString());
            }
        }
        try {
            if (StrUtil.equals("1", courseDto.getIsTrain())) {
                trainCourseService.updateTrainCourse(courseDto);
            } else if(StrUtil.equals("0", courseDto.getIsTrain())){
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
    @ApiOperation(value = "查询课程",httpMethod="POST",response = PageInfo.class)
    @ApiImplicitParam(name = "courseDto",value = "对象",required = true,paramType = "body")
    public ResponseBean queryCourse(@RequestBody CourseDto courseDto) {
        ResponseBean bean = new ResponseBean();

        if (StrUtil.equals("1", courseDto.getIsTrain())) {
            //获取所有课程信息
            PageInfo<TrainCourseVo> pageInfo = PageHelper.startPage(courseDto).doSelectPageInfo(() -> trainCourseService.selectTrainCourse(courseDto));
            bean.addSuccess(pageInfo);
        } else if(StrUtil.equals("0", courseDto.getIsTrain())){
            //获取所有课程信息
            PageInfo<Course> pageInfo = PageHelper.startPage(courseDto).doSelectPageInfo(() -> courseService.selectCourse(courseDto));
            bean.addSuccess(pageInfo);
        }
        return bean;
    }

}
