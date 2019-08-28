package com.rs.teach.service.sysCode.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.sysCode.dao.SysCodeMapper;
import com.rs.teach.mapper.sysCode.entity.SysCode;
import com.rs.teach.service.sysCode.SysCodeService;

@Service
public class SysCodeServiceImpl implements SysCodeService{
	
	@Autowired
	private SysCodeMapper mapper;
	
	@Override
	public List<SysCode> getSysCodeList(String codeType) {
		return mapper.querySysCodeList(codeType);
	}
	
}