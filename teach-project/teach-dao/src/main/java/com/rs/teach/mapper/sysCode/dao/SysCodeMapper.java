package com.rs.teach.mapper.sysCode.dao;

import java.util.List;

import com.rs.teach.mapper.sysCode.entity.SysCode;

/**
* SysCodeMapper.java
* @Description:字典dao
* @author: suzhao
* @date: 2019年8月27日 下午6:19:50
* @version: V1.0
*/
public interface SysCodeMapper{
	public List<SysCode> querySysCodeList(String codeType);
	
	public void delSysCode(String cid);
	
	public SysCode querySysCode(String cid);
	
	public int insertSysCode(SysCode sysCode);
	
	public int updateSysCode(SysCode sysCode);
}