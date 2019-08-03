package com.rs.teach.service.training;

import com.rs.teach.mapper.section.vo.TrainSectionVo;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:10
 */
public interface TrainSectionService {

    List<TrainSectionVo> selectAllSection(String courseId);

}
