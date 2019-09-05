package com.rs.teach.mapper.common;

/**
 * @author 汪航
 * @Description 下拉列表（返回给前端）
 * @create 2019-09-03 15:12
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class OptionVo implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Object value;

    protected Object label;

    public OptionVo() {
    }

    public OptionVo(Object value, Object label) {
        this.value = value;
        this.label = label;
    }

}
