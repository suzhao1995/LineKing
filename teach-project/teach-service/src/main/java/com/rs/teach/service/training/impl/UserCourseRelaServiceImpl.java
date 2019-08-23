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
    public Integer studyStatus(String userId, String courseId) {

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
        //修改状态标识为2
        userCourseRelaMapper.cancel(userId, courseId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.NO_JOIN.name()));
    }

	@Override
	public int addCourse(String courseId, String userId, String classId, String relaType) {
		return userCourseRelaMapper.insertCourse(courseId, userId, classId,relaType);
	}

	@Override
	public void addAllSection(String courseId, String userId, String classId, String relaType) {
		if("0".equals(relaType)){
			userCourseRelaMapper.insertAllSection(courseId, userId, classId,relaType);
		}else if("4".equals(relaType)){
			userCourseRelaMapper.insertVideoSection(courseId, userId, classId, relaType);
		}
	}

	@Override
	public int isAddCourse(String courseId, String userId, String classId) {
		int count = userCourseRelaMapper.isAddCourse(userId, courseId, "2", classId);
		return count;
	}

	@Override
	public void modifyRelaType(String courseId, String userId, String classId, String courseType) {
		//修改课程资源relaType = 1； 章节relaType = 0
		if("1".equals(courseType)){
			
			int result1 = userCourseRelaMapper.updateCourseRela(userId, courseId, "1", classId);
			if(result1 > 0 ){
				userCourseRelaMapper.updateSectionRela(userId, courseId, "0", classId);
			}
		}else if("2".equals(courseType)){
			int result1 = userCourseRelaMapper.updateCourseRela(userId, courseId, "3", classId);
			if(result1 > 0 ){
				userCourseRelaMapper.updateSectionRela(userId, courseId, "4", classId);
			}
		}
	}

	@Override
	public void cancelCourse(String courseId, String userId, String classId) {
		//relaType = 2 为课程取消状态 
		userCourseRelaMapper.updateRela(userId, courseId, "2", classId);
	}

}
