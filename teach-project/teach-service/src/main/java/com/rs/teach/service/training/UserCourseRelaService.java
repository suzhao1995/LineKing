package com.rs.teach.service.training;

import com.rs.teach.mapper.section.entity.UserCourseRela;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:10
 */
public interface UserCourseRelaService {
    /**
     * 判断是否加入我的课程（1：加入，2和0都是未加入）
     * @param courseId
     * @param userId
     * @return
     */
    Integer studyStatus(String courseId, String userId);

    void join(String courseId,String userId);

    void cancel(String userId, String courseId);

    /**
     * 查询学习状态(已加入的才能查)
     * @param courseId
     * @param userId
     * @return
     */
    List<UserCourseRela> selectIsFinish(String courseId, String userId);

    void updateIsFinish(String trainCourseId, String userId, String sectionId, Integer isFinish);
    
}
