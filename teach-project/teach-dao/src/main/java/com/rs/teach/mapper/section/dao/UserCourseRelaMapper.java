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
    void updateIsFinish(@Param("trainCourseId") String trainCourseId, @Param("userId")String userId, @Param("sectionId")String sectionId, @Param("isFinish")Integer isFinish,@Param("classId") String classId);

    /**
     * 添加课程信息进入我的课程
      * @param courseId
     * @param userId
     * @param classId
     * @return
     */
    int insertCourse(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId, @Param("relaType") String relaType);

    /**
     * 添加课程的章节信息信息进入我的课程
     * @param courseId
     * @param userId
     * @param classId
     */
    void insertAllSection(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId,@Param("relaType") String relaType);
    
    /**
     * 添加视频课程的章节信息信息进入我的课程
     * @param courseId
     * @param userId
     * @param classId
     */
    void insertVideoSection(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId,@Param("relaType") String relaType);

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
    
    /**
    * 查询教师是否添加过该课程到对应班级 
    * @param 
    * @throws
    * @return int
    * @author suzhao
    * @date 2019年8月23日 上午10:43:39
    */
    int isAddCourse(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType") String relaType,@Param("classId") String classId);

}
