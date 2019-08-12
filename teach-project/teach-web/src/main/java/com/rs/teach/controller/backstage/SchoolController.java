package com.rs.teach.controller.backstage;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.backstage.entity.School;
import com.rs.teach.mapper.common.PageDto;
import com.rs.teach.mapper.user.dao.UserMapper;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.backstage.SchoolService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private UserMapper userMapper;


    /**
     *
     * 判断用户是否为管理员
     * @param sessionKey
     * @throws Exception
     */
    private void checkPremission(String sessionKey) throws Exception {
        String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
        User user = userMapper.getUserById(userId);
        if (!StrUtil.equals("1", user.getAdminFlag())) {
            throw new Exception("该用户没有权限");
        }
    }

    /**
     * 添加学校
     *
     * @param school
     * @param sessionKey
     * @return
     */
    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addSchool(School school, String sessionKey) {
        ResponseBean bean = new ResponseBean();
        try {
            checkPremission(sessionKey);
            schoolService.addSchool(school);
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("学校-新增-失败", e);
            bean.addError(e.getMessage());
        }
        return bean;
    }

    /**
     * @param schoolId
     * @param sessionKey
     * @return
     */
    @RequestMapping(value = "/deleteSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean deleteSchool(@RequestBody String schoolId, String sessionKey) {
        ResponseBean bean = new ResponseBean();
        try {
            checkPremission(sessionKey);
            schoolService.deleteSchool(schoolId);
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("学校-新增-失败", e);
            bean.addError(e.getMessage());
        }
        return bean;
    }

    @RequestMapping(value = "/updateSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateSchool(School school,String sessionKey) {
        ResponseBean bean = new ResponseBean();
        try {
            checkPremission(sessionKey);
            schoolService.updateSchool(school);
            bean.addSuccess();
        } catch (Exception e) {
            logger.error("学校-新增-失败", e);
            bean.addError(e.getMessage());
        }
        return bean;
    }

    /**
     * 查询所有学校信息
     * @param pageDto{pageNum,pageSize}
     * @return
     */
    @RequestMapping(value = "/selectSchool", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectSchool(PageDto pageDto) {
        ResponseBean bean = new ResponseBean();
        PageInfo<School> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() -> schoolService.selectSchool());
        bean.addSuccess(pageInfo);
        return bean;
    }

}
