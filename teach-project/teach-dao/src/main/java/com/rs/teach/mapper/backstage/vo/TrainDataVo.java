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
public class TrainDataVo implements Serializable {

    private static final long serialVersionUID = -8574558481960629917L;

    private String trainDataName;   //培训考核标题名
    private Integer trainDataNum;   //培训考核参与人数（按照添加考核时勾选的人来统计的）
}
