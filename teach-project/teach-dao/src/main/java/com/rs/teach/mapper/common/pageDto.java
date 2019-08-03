package com.rs.teach.mapper.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-02 12:37
 */
public class pageDto implements Serializable {
    private static final long serialVersionUID = 626251076886681368L;

    private int pageSize;   //每页记录数
    private int pageNum;    //第几页

    public pageDto() {
        super();
    }

    public pageDto(int pageSize, int pageNum) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        if (pageNum < 1) {
            this.pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        if (pageSize < 1) {
            this.pageSize = 20;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
