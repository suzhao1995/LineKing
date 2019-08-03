package com.rs.teach.mapper.section.dao;

import com.rs.teach.mapper.section.vo.TrainSectionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-03 11:25
 */
public interface TrainSectionMapper {

    List<TrainSectionVo> selectAllSection(@Param("courseId")String courseId);

}
