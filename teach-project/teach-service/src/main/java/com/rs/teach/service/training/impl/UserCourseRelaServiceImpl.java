package com.rs.teach.service.training.impl;

import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.common.Enums.CourseStatusEnum;
import com.rs.teach.mapper.common.Enums.RelaTypeEnum;
import com.rs.teach.mapper.note.entity.CourseNote;
import com.rs.teach.mapper.section.dao.UserCourseRelaMapper;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.mapper.section.entity.UserCourseRela;
import com.rs.teach.mapper.summary.entity.Summary;
import com.rs.teach.service.note.CourseNoteService;
import com.rs.teach.service.training.SummaryService;
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

    /**
     * 课程笔记表service
     */
    @Autowired
    private CourseNoteService courseNoteService;

    @Autowired
    private TrainSectionService trainSectionService;

    @Autowired
    private SummaryService summaryService;

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
        List<UserCourseRela> list = userCourseRelaMapper.selectIsFinish(courseId, userId);
        return list;
    }

    @Override
    public void updateIsFinish(String trainCourseId, String userId, String sectionId, Integer isFinish) {
        userCourseRelaMapper.updateIsFinish(trainCourseId, userId, sectionId, isFinish);
    }

    @Override
    public String join(String sectionId, String courseId, String userId) {
        //修改状态标识为1，section_id课程和用户根节点，为0代表根节点，其他对应章节表sectionId
        userCourseRelaMapper.join(userId, courseId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.JOIN.name()));
        //批量插入（课程用户关联表）
        userCourseRelaMapper.addAll(userId, courseId, CourseStatusEnum.convent2TableNum(CourseStatusEnum.NO_SIGN.name()), RelaTypeEnum.convent2TableNum(RelaTypeEnum.END.name()));
        //批量插入（笔记表）
        CourseNote courseNote = new CourseNote();
        courseNote.setUserId(userId);
        courseNote.setCourseId(courseId);
        courseNote.setNote("");
        courseNoteService.addAllNote(courseNote);

        //查询笔记信息
        if (!StrUtil.isEmpty(sectionId)) {
            courseNote.setSectionId(sectionId);
            return courseNoteService.selectNote(courseNote);
        }
        return null;
    }

    @Override
    public void addRoot(String userId, String courseId) {
        userCourseRelaMapper.addRoot(userId, courseId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.NO_JOIN.name()));
    }

    @Override
    public void cancel(String userId, String courseId) {
        //修改状态标识为2，section_id课程和用户根节点，为0代表根节点，其他对应章节表sectionId
        userCourseRelaMapper.cancel(userId, courseId, RelaTypeEnum.convent2TableNum(RelaTypeEnum.NO_JOIN.name()));
    }

}
