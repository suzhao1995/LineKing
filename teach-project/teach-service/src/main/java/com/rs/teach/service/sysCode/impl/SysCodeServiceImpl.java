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

	@Override
	public void delSysCode(String cid) {
		mapper.delSysCode(cid);
	}

	@Override
	public SysCode getSysCode(String cid) {
		return mapper.querySysCode(cid);
	}

	@Override
	public int addSysCode(SysCode sysCode) {
		return mapper.insertSysCode(sysCode);
	}

	@Override
	public int modifySysCode(SysCode sysCode) {
		return mapper.updateSysCode(sysCode);
	}

	@Override
	public SysCode getSysCodeByCode(String code, String codeType) {
		
		return mapper.querySysCodeByCode(code,codeType);
	}
	
}