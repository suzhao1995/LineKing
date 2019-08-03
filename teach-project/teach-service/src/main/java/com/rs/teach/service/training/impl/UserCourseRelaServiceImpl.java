package com.rs.teach.service.training.impl;

import com.rs.teach.mapper.section.dao.TrainSectionMapper;
import com.rs.teach.mapper.section.dao.UserCourseRelaMapper;
import com.rs.teach.mapper.section.entity.UserCourseRela;
import com.rs.teach.mapper.section.vo.TrainSectionVo;
import com.rs.teach.service.training.TrainSectionService;
import com.rs.teach.service.training.UserCourseRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:21
 */
@Service
public class UserCourseRelaServiceImpl implements UserCourseRelaService {

    @Autowired
    private UserCourseRelaMapper userCourseRelaMapper;

    @Override
    public List<UserCourseRela> studyStatus(String courseId, String userId) {

        //判断关联类型(为1时即为添加了此课程，为0反之即设置isFinish为待学习)
        int result = userCourseRelaMapper.isNotEmpty(courseId,userId);
        if(result > 0 ){
            //查询所有小章节的状态
            List<UserCourseRela> list = userCourseRelaMapper.selectStatus(courseId,userId);
            return list;
        }else{
            return null;
        }
    }
}
