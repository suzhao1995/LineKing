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
    
    /**
    * 添加课程信息到我的课程
    * @param 
    * @throws
    * @return int
    * @author suzhao
    * @date 2019年8月23日 上午10:52:39
    */
    int addCourse(String courseId, String userId, String classId, String relaType);
    
    void addAllSection(String courseId, String userId, String classId, String relaType);
    
    int isAddCourse(String courseId, String userId, String classId);
    
    /**
    * 修改relaType的状态码
    * @param courseType = 1： 修改普通课件，  courseType = 2 修改视频课件
    * @throws
    * @return void
    * @author suzhao
    * @date 2019年8月23日 上午10:50:26
    */
    void modifyRelaType(String courseId, String userId, String classId,String courseType);
    
    void cancelCourse(String courseId, String userId, String classId); 
}
