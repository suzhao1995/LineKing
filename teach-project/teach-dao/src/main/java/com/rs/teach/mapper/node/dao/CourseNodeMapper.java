package com.rs.teach.mapper.node.dao;

import com.rs.teach.mapper.node.entity.CourseNode;
import org.apache.ibatis.annotations.Param;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:28
 */
public interface CourseNodeMapper {
    CourseNode selectNode(@Param("trainCourseId") String trainCourseId, @Param("userId")String userId, @Param("sectionId") String sectionId);

    void saveNode(CourseNode courseNode);
}
