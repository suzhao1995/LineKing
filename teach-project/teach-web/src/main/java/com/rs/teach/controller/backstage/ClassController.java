package com.rs.teach.controller.backstage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.entity.TFClass;
import com.rs.teach.mapper.common.PageDto;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.backstage.ClassService;
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
 * @Description
 * @create 2019-08-12 12:53
 */
@Controller
@RequestMapping("/class")
public class ClassController {

    private static final Logger logger = Logger.getLogger(ClassController.class);

    @Autowired
    private ClassService classService;

    @Autowired
    private UserService userService;


    /**
     * 根据校区id查询所有班级
     * @param tfClass
     * @return
     */
    @RequestMapping(value = "/selectClassBySchoolId" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectClassBySchoolId(@RequestBody TFClass tfClass){
        ResponseBean bean = new ResponseBean();
        List<TFClass> result = classService.selectClassBySchoolId(tfClass);
        bean.addSuccess(result);
        return bean;
    }

    /**
     * 根据校区id查询所有教师
     * @param user
     * @return
     */
    @RequestMapping(value = "/selectTeachBySchoolId" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectTeachBySchoolId(@RequestBody User user){
        ResponseBean bean = new ResponseBean();
        List<User> result = userService.selectTeachBySchoolId(user.getSchoolId());
        bean.addSuccess(result);
        return bean;
    }

    /**
     * 添加班级
     * @param tfClass
     * @return
     */
    @RequestMapping(value = "/addClass" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addClass(@RequestBody TFClass tfClass){
        ResponseBean bean = new ResponseBean();
        try {
            classService.addClass(tfClass);
            bean.addSuccess();
        }catch (Exception e){
            logger.error("添加班级-异常",e);
            bean.addError("添加班级失败！");
        }
        return bean;
    }

    /**
     * 删除班级
     * @param tfClass
     * @return
     */
    @RequestMapping(value = "/deleteClass" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean deleteClass(@RequestBody TFClass tfClass){
        ResponseBean bean = new ResponseBean();
        String classId = tfClass.getClassId();
        try {
            classService.deleteClass(classId);
            bean.addSuccess();
        }catch (Exception e){
            logger.error("删除班级-异常",e);
            bean.addError("删除班级失败！");
        }
        return bean;
    }

    /**
     * 修改班级
     * @param tfClass
     * @return
     */
    @RequestMapping(value = "/updateClass" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateClass(@RequestBody TFClass tfClass){
        ResponseBean bean = new ResponseBean();
        try {
            classService.updateClass(tfClass);
            bean.addSuccess();
        }catch (Exception e){
            logger.error("修改班级-异常",e);
            bean.addError("修改班级失败！");
        }
        return bean;
    }

    /**
     * 查询所有班级
     *
     * @param pageDto
     * @return
     */
    @RequestMapping(value = "/selectClass" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectClass(@RequestBody PageDto pageDto){
        ResponseBean bean = new ResponseBean();
        PageInfo<TFClass> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() -> classService.selectClass());
        bean.addSuccess(pageInfo);
        return bean;
    }

}
