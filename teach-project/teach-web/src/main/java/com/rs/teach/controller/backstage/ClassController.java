package com.rs.teach.controller.backstage;

import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.entity.TFClass;
import com.rs.teach.service.backstage.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-12 12:53
 */

@Controller
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @RequestMapping(value = "/addclass" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addClass(TFClass tfClass, String sessionKey){
        ResponseBean bean = new ResponseBean();
        try {
            classService.addclass(tfClass);
        }catch (Exception e){

        }
        return bean;
    }

}
