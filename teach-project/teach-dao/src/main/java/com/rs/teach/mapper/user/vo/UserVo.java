package com.rs.teach.mapper.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanghang
 * @Description
 * @create 2019-09-12 12:10
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = -8278761446096970338L;
    private String userId; 		//用户名
    private String userName;	//用户名称
    private String passWord;	//用户登录密码
    private String schoolId;	//校区id
    private String endDate;		//账号到期时间
    private String serialNumber;	//用户手机号码
}
