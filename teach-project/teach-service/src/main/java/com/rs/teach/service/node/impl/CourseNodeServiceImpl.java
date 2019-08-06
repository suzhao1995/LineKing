package com.rs.teach.service.node.impl;

import com.rs.teach.mapper.node.dao.CourseNodeMapper;
import com.rs.teach.mapper.node.entity.CourseNode;
import com.rs.teach.service.node.CourseNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:25
 */
@Service
public class CourseNodeServiceImpl implements CourseNodeService {

    @Autowired
    private CourseNodeMapper nodeMapper;

    @Override
    public CourseNode selectNode(String trainCourseId, String userId, String sectionId) {

        CourseNode courseNode = nodeMapper.selectNode(trainCourseId, userId, sectionId);
        return courseNode;
    }

    @Override
    public void saveNode(CourseNode courseNode) {
        nodeMapper.saveNode(courseNode);
    }
}
