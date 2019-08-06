package com.rs.teach.service.training;

import com.rs.teach.mapper.section.entity.UserCourseRela;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:10
 */
public interface UserCourseRelaService {

    Integer studyStatus(String courseId, String userId);

    void join(String userId, String courseId);

    void addRoot(String userId, String courseId);

    void cancel(String userId, String courseId);

    List<UserCourseRela> selectIsFinish(String courseId, String userId);

    void updateIsFinish(String trainCourseId, String userId, String sectionId, Integer isFinish);
}
