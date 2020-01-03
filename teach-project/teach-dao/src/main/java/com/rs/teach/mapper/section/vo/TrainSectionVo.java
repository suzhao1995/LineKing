package com.rs.teach.mapper.section.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.video.entity.VideoSection;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 16:53
 */
@Data
public class TrainSectionVo implements Serializable,Comparable<TrainSectionVo>{

    private static final long serialVersionUID = -442561324686250187L;

    //培训大章节主键id
    private String id;

    //培训大章节序号
    private String trainSectionSort;

    //培训大章节名称
    private String trainSectionName;

    //大章节下的小章节集合
    private List<TrainLitterSectionVo> trainLitterSectionVoList;
    
    //课程资源大章节下的小章节集合
    private List<Section> sectionList;
    
    //包含学习状态的小章节集合
    private List<Map<String,Object>> sectionStatusList;
    
    //视频课程资源大章节下的小章节集合
    private List<VideoSection> VideoList;

	@Override
	public int compareTo(TrainSectionVo o) {
		return Integer.valueOf(this.trainSectionSort) - Integer.valueOf(o.trainSectionSort);
	}
    
    
}
