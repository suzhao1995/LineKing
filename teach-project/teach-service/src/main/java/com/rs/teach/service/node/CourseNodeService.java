package com.rs.teach.service.node;

import com.rs.teach.mapper.node.entity.CourseNode;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:24
 */
public interface CourseNodeService {
    CourseNode selectNode(String trainCourseId, String userId, String sectionId);

    void saveNode(CourseNode courseNode);
}
