package com.rs.teach.controller.backstage;

import cn.hutool.core.util.ArrayUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.mapper.backstage.vo.SchoolVo;
import com.rs.teach.mapper.common.ConditionExtVo;
import com.rs.teach.mapper.common.PageDto;
import com.rs.teach.service.backstage.SchoolCourseService;
import com.rs.teach.service.backstage.SchoolService;
import com.rs.teach.service.studyAttr.CourseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 汪航
 * @Description 学校controller
 * @create 2019-08-12 10:59
 */
@Controller
@RequestMapping("/school")
public class SchoolController {

    private static final Logger logger = Logger.getLogger(SchoolController.class);

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private SchoolCourseService schoolCourseService;


    /**
     * 查询权限课程(树状结构)
     * @return
     */
    @RequestMapping(value = "/queryConditionExtVo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean queryConditionExtVo() {
        ResponseBean bean = new ResponseBean();
        List<ConditionExtVo> list = courseService.queryOptionVo();
        bean.addSuccess(list);
        return bean;
    }


    /**
     * 回显权限课程（数组）
     * @return
     */
    @RequestMapping(value = "/echoCourse", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean echoCourse(@RequestBody School school) {
        ResponseBean bean = new ResponseBean();
        String [] courses = schoolCourseService.echoCourse(school.getSchoolId());
        bean.addSuccess(courses);
        return bean;
    }

    /**
     * 回显权限视频（数组）
     * @return
     */
    @RequestMapping(value = "/echoVideo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean echoVideo(@RequestBody School school) {
        ResponseBean bean = new ResponseBean();
        String [] videos = schoolCourseService.echoCourse(school.getSchoolId());
        bean.addSuccess(videos);
        return bean;
    }

    /**
     * 添加学校
     * @param school
     * @return
     */
    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addSchool(@RequestBody School school) {
        ResponseBean bean = new ResponseBean();
        try {
            //此学校是否存在
            Integer result = schoolService.isEmpty(school);
            if (result > 0) {
                bean.addError(ResponseBean.CODE_REPEATEDADD_ERROR,"不能重复添加学校！");
                return bean;
            }
            schoolService.addSchool(school);
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("学校-新增-失败", e);
            bean.addError(e.getMessage());
        }
        return bean;
    }

    /**
     * 校区删除
     * @param school
     * @return
     */
    @RequestMapping(value = "/deleteSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean deleteSchool(@RequestBody School school) {
        ResponseBean bean = new ResponseBean();
        String schoolId = school.getSchoolId();
        try {
            schoolService.deleteSchool(schoolId);
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("学校-刪除-失败", e);
            bean.addError(e.getMessage());
        }
        return bean;
    }

    /**
     * 修改学校信息（或修改权限课程）
     * @param school
     * @return
     */
    @RequestMapping(value = "/updateSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateSchool(@RequestBody School school) {
        ResponseBean bean = new ResponseBean();
        try {
            schoolService.updateSchool(school);
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("学校-修改-失败", e);
            bean.addError(e.getMessage());
        }
        return bean;
    }

    /**
     * 修改学校信息时数据回显
     * @param school
     * @return
     */
    @RequestMapping(value = "/selectSchoolBySchoolId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectSchoolBySchoolId(@RequestBody School school) {
        ResponseBean bean = new ResponseBean();
        SchoolVo schoolVo = schoolService.selectSchoolBySchoolId(school);
        bean.addSuccess(schoolVo);
        return bean;
    }

    /**
     * 查询所有学校信息
     * @param pageDto{pageNum,pageSize}
     * @return
     */
    @RequestMapping(value = "/selectSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectSchool(@RequestBody PageDto pageDto) {
        ResponseBean bean = new ResponseBean();
        PageInfo<SchoolVo> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() -> schoolService.selectSchoolVo());
        bean.addSuccess(pageInfo);
        return bean;
    }
}
