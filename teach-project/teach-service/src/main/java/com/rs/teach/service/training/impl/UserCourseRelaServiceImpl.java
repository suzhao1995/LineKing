package com.rs.teach.service.training.impl;

import com.rs.teach.mapper.common.Enums.CourseStatusEnum;
import com.rs.teach.mapper.common.Enums.RelaTypeEnum;
import com.rs.teach.mapper.section.dao.UserCourseRelaMapper;
import com.rs.teach.mapper.section.entity.UserCourseRela;
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
    public Integer studyStatus(String courseId, String userId) {

        //判断是否为JOIN
        Integer resultJOIN = userCourseRelaMapper.studyStatus(courseId, userId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.JOIN.name()));
        if (resultJOIN > 0) {
            return RelaTypeEnum.convent2TableNum(RelaTypeEnum.JOIN.name());
        }
        //判断是否为NO_JOIN
        Integer resultNOJOIN = userCourseRelaMapper.studyStatus(courseId, userId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.NO_JOIN.name()));
        if (resultNOJOIN > 0) {
            return RelaTypeEnum.convent2TableNum(RelaTypeEnum.NO_JOIN.name());
        }
        return RelaTypeEnum.convent2TableNum(RelaTypeEnum.END.name());
    }

    @Override
    public List<UserCourseRela> selectIsFinish(String courseId, String userId) {
        List<UserCourseRela> list = userCourseRelaMapper.selectIsFinish(courseId, userId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.JOIN.name()));
        return list;
    }

    @Override
    public void updateIsFinish(String trainCourseId, String userId, String sectionId, Integer isFinish) {
        userCourseRelaMapper.updateIsFinish(trainCourseId, userId, sectionId, isFinish);
    }

    @Override
    public void join(String courseId, String userId) {
        //查看是否已添加
        int result = userCourseRelaMapper.isEmptyJoin(userId, courseId);
        if (result > 0) {
            //修改RelaType标志为1
            userCourseRelaMapper.updateRelaType(userId, courseId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.JOIN.name()));
        } else {
            //批量插入（课程用户关联表）
            userCourseRelaMapper.addAll(userId, courseId, CourseStatusEnum.convent2TableNum(CourseStatusEnum.NO_SIGN.name()), RelaTypeEnum.convent2TableNum(RelaTypeEnum.JOIN.name()));
        }
    }


    @Override
    public void cancel(String userId, String courseId) {
        //修改状态标识为2，//section_id课程和用户根节点，为0代表根节点，其他对应章节表sectionId
        userCourseRelaMapper.cancel(userId, courseId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.NO_JOIN.name()));
    }

	@Override
	public int addCourse(String courseId, String userId, String classId) {
		return userCourseRelaMapper.insertCourse(courseId, userId, classId);
	}

	@Override
	public void addAllSection(String courseId, String userId, String classId) {
		userCourseRelaMapper.insertAllSection(courseId, userId, classId);
	}

}
