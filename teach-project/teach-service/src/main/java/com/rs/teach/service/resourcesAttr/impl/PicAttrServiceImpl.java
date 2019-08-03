package com.rs.teach.service.resourcesAttr.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.resourcesAttr.dao.PicAttrMapper;
import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;
import com.rs.teach.service.resourcesAttr.PicAttrService;

@Service
public class PicAttrServiceImpl implements PicAttrService{
	
	@Autowired
	private PicAttrMapper mapper;
	
	@Override
	public int modifyPic(PicAttr pic) {
		return mapper.updatePic(pic);
	}

	@Override
	public int addPic(PicAttr pic) {
		return mapper.insertPic(pic);
	}

	@Override
	public PicAttr getPic(String associationId) {
		return mapper.queryPic(associationId);
	}
	
}