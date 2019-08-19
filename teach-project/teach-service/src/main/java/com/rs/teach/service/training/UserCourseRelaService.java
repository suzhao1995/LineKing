package com.rs.teach.service.training;

import com.rs.teach.mapper.section.entity.UserCourseRela;

import java.util.List;

/**
 * @author 汪航
 * @Description 课程用户关联service
 * @create 2019-08-03 11:10
 */
public interface UserCourseRelaService {
    /**
     * 判断是否加入我的课程（1：加入，2和0都是未加入）
     * @param courseId
     * @param userId
     * @return
     */
    Integer studyStatus(String userId, String courseId);

    /**
     * 加入我的课程
     * @param courseId
     * @param userId
     */
    void join(String courseId,String userId);
    /**
     * 取消我的课程
     * @param courseId
     * @param userId
     */
    void cancel(String userId, String courseId);

    /**
     * 查询学习状态(已加入的才能查)
     * @param courseId
     * @param userId
     * @return
     */
    List<UserCourseRela> selectIsFinish(String courseId, String userId);

    /**
     * 修改学习状态
     * @param trainCourseId
     * @param userId
     * @param sectionId
     * @param isFinish
     */
    void updateIsFinish(String trainCourseId, String userId, String sectionId, Integer isFinish);
    
    int addCourse(String courseId, String userId, String classId);
    
    void addAllSection(String courseId, String userId, String classId);
    
    int getRelaType(String courseId, String userId);
    
    void modifyRelaType(String courseId, String userId, String classId);
    
    void cancelCourse(String courseId, String userId, String classId); 
}
