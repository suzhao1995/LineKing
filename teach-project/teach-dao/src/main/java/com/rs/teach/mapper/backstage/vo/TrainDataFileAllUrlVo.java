package com.rs.teach.mapper.backstage.vo;

import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghang
 * @Description  考核系列文件路径返回
 * @create 2019-09-05 15:16
 */
@Data
public class TrainDataFileAllUrlVo implements Serializable {

    private static final long serialVersionUID = 4292467471121769815L;

    private String trainDataUrl; //考核文件本地地址
    private String trainAnswerUrl; //考核文件答案本地地址
    private List<TrainDataAnswer> list; //考核人员上传答卷本地地址
}
