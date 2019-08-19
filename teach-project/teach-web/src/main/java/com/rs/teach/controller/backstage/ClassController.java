package com.rs.teach.controller.backstage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.entity.TFClass;
import com.rs.teach.mapper.common.PageDto;
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

    @RequestMapping(value = "/selectClass" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectClass(@RequestBody PageDto pageDto){
        ResponseBean bean = new ResponseBean();
        PageInfo<TFClass> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() -> classService.selectClass());
        bean.addSuccess(pageInfo);
        return bean;
    }
}