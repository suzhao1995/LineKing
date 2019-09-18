package com.rs.teach.service.User.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.common.ConditionExtVo;
import com.rs.teach.mapper.resourcesAttr.dao.PicAttrMapper;
import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;
import com.rs.teach.mapper.user.dao.UserMapper;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.mapper.user.vo.UserVo;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.resourcesAttr.PicAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper dao;

    @Autowired
    private PicAttrMapper mapper;

    @Override
    public String test() {
        return dao.getUserById("0001").getUserName();
    }

    @Autowired
    private PicAttrService picAttrService;

    /**
     * 根据ID查询用户信息
     *
     * @param id
     * @return user
     * @throws
     * @author suzhao
     * @date 2019年7月22日 下午5:00:49
     */
    @Override
    public User getUserById(String id) {
        User user = dao.getTeachUser(id);
        User userAttr = dao.getUserById(id);
        if (userAttr != null) {
            user.setAttr(userAttr.getAttr());
        }
        return user;
    }


    @Override
    public boolean isModifyInfo(String id) {
        int count = dao.isUpdateInfo(id);
        if (count == 0) {
            return false;
        }
        return true;
    }


    @Override
    public int modifyUser(User user) {
        return dao.updateUser(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addUserAndPic(User user, PicAttr picAttr) {
        try {
            mapper.insertPic(picAttr);
            dao.addUser(user);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Integer checkUserId(String userId) {
        //用户登录账号校验
        Integer count = dao.checkUserId(userId);
        return count;
    }

    @Override
    public Integer checkTelNum(String serialNumber) {
        return dao.checkTelNum(serialNumber);
    }

    @Override
    public List<User> selectUserInfo(User user) {

        List<User> userList = dao.selectUserInfo(user);
        return userList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateUserInfoAndPic(User user, PicAttr picAttr) {
        int i = 0;
        try {
            dao.updateUserInfo(user);
            if (StrUtil.isNotBlank(picAttr.getPicId())) {
                i = picAttrService.modifyPic(picAttr);
            }
        } catch (Exception e) {
            throw e;
        }
        return i;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUser(String userId) {
        try {
            //删除用户
            dao.deleteUser(userId);
            //删除用户的头像
            mapper.deletePic(userId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<User> queryUserNotIn() {

        return dao.queryUserNotIn();
    }

    @Override
    public List<User> selectTeachBySchoolId(String schoolId) {

        return dao.selectTeachBySchoolId(schoolId);
    }

    @Override
    public UserVo selectUserInfoById(String userId) {

        return dao.selectUserInfoById(userId);
    }

    @Override
    public List<ConditionExtVo> queryOptionVo() {
        List<ConditionExtVo> typeAllList = dao.schoolBy();
        for (ConditionExtVo typeVo : typeAllList) {
            // 用户
            List<ConditionExtVo> levAllList = dao.userBy(typeVo.getId());
            if (CollUtil.isNotEmpty(levAllList)) {
                typeVo.setChildren(levAllList);
            }
        }
        return typeAllList;
    }


}
