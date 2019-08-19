package com.rs.teach.mapper.section.dao;

import com.rs.teach.mapper.section.entity.UserCourseRela;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:25
 */
public interface UserCourseRelaMapper {

    void addRoot(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType")Integer relaType);

    /**
     * 判断标识符
     * @param courseId
     * @param userId
     * @param relaType
     * @return
     */
    Integer studyStatus(@Param("courseId") String courseId, @Param("userId") String userId, @Param("relaType")Integer relaType);

    /**
     * 查询学习状态(已加入的才能查)
     * @param courseId
     * @param userId
     * @return
     */
    List<UserCourseRela> selectIsFinish(@Param("courseId")String courseId, @Param("userId")String userId,@Param("relaType")Integer relaType);

    /**
     * 取消我的课程
     * @param userId
     * @param courseId
     * @param relaType
     */
    void cancel(@Param("userId") String userId, @Param("courseId") String courseId , @Param("relaType")Integer relaType);

    /**
     * 修改学习状态
     * @param trainCourseId
     * @param userId
     * @param sectionId
     * @param isFinish
     */
    void updateIsFinish(@Param("trainCourseId") String trainCourseId, @Param("userId")String userId, @Param("sectionId")String sectionId, @Param("isFinish")Integer isFinish);

    /**
     * 添加课程信息进入我的课程
      * @param courseId
     * @param userId
     * @param classId
     * @return
     */
    int insertCourse(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId);

    /**
     * 添加课程的章节信息信息进入我的课程
     * @param courseId
     * @param userId
     * @param classId
     */
    void insertAllSection(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId);

    /**
     * 查看是否已添加
     * @param userId
     * @param courseId
     * @return
     */
    int isEmptyJoin(@Param("userId") String userId, @Param("courseId") String courseId);

    /**
     * 修改RelaType标志为1（即加入我的课程）
     * @param userId
     * @param courseId
     * @param relaType
     */
    void updateRelaType(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType") Integer relaType);

    /**
     * 批量插入（课程用户关联表）
     * @param userId
     * @param courseId
     * @param isfinish
     * @param relaType
     */
    void addAll(@Param("userId") String userId, @Param("courseId") String courseId,@Param("isfinish")Integer isfinish,@Param("relaType")Integer relaType);
    
    int updateCourseRela(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType") String relaType,@Param("classId") String classId);
    
    int updateSectionRela(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType") String relaType,@Param("classId") String classId);
    
    void updateRela(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType") String relaType,@Param("classId") String classId);
}
