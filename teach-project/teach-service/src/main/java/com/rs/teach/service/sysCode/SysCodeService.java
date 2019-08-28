package com.rs.teach.service.sysCode;

import java.util.List;

import com.rs.teach.mapper.sysCode.entity.SysCode;

/**
* SysCodeService.java
* @Description:字典service
* @author: suzhao
* @date: 2019年8月27日 下午6:25:34
* @version: V1.0
*/
public interface SysCodeService{
	public List<SysCode> getSysCodeList(String codeType);
}