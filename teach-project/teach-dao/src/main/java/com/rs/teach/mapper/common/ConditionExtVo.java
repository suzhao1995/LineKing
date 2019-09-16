package com.rs.teach.mapper.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-12 17:24
 */
@Data
public class ConditionExtVo implements Serializable {
    private static final long serialVersionUID = -3821423920909071284L;

    private String id;

    private String label;

    private List<ConditionExtVo> children;
}
