package com.rs.teach.mapper.backstage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 @author 汪航
 @Description  学校表
 @create 2019-08-12 10:55 
 */
@Data
public class School implements Serializable {

    private static final long serialVersionUID = -9167105108960399270L;

    private String schoolId;      //学校ID
    private String schoolName;    //学校name
    private String schoolAddress;     //学校地址

}
