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

    Integer studyStatus(@Param("courseId") String courseId, @Param("userId") String userId, @Param("relaType")Integer relaType);

    void addRoot(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType")Integer relaType);

    List<UserCourseRela> selectIsFinish(@Param("courseId")String courseId, @Param("userId")String userId,@Param("relaType")Integer relaType);

    void join(@Param("userId") String userId, @Param("courseId") String courseId ,@Param("relaType")Integer relaType);

    void cancel(@Param("userId") String userId, @Param("courseId") String courseId , @Param("relaType")Integer relaType);

    void updateIsFinish(@Param("trainCourseId") String trainCourseId, @Param("userId")String userId, @Param("sectionId")String sectionId, @Param("isFinish")Integer isFinish);

    int insertCourse(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId);

    void insertAllSection(@Param("courseId") String courseId, @Param("userId") String userId, @Param("classId") String classId);

    /**
     * 查看是否已添加
     * @param userId
     * @param courseId
     * @return
     */
    int isEmptyJoin(@Param("userId") String userId, @Param("courseId") String courseId);

    void updateRelaType(@Param("userId") String userId, @Param("courseId") String courseId, @Param("relaType") Integer relaType);

    void addAll(@Param("userId") String userId, @Param("courseId") String courseId,@Param("isfinish")Integer isfinish,@Param("relaType")Integer relaType);
    
}
