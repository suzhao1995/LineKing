package com.rs.teach.service.materiel.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rs.teach.mapper.materiel.dao.MaterielMapper;
import com.rs.teach.mapper.materiel.entity.Materiel;
import com.rs.teach.service.materiel.MaterielService;

@Service
public class MaterielServiceImpl implements MaterielService{
	
	@Autowired
	private MaterielMapper mapper;
	
	@Override
	public List<Materiel> getMateriel(String adminOperation, String code) {
		return mapper.queryMateriel(adminOperation,code);
	}

	@Override
	public int modifyStatus(String materielId, String status) {
		return mapper.updateStatus(materielId,status);
	}

	@Override
	public Materiel getMaterielById(String materielId) {
		return mapper.queryMaterielById(materielId);
	}

	@Override
	public int addMateriel(Materiel materiel) {
		return mapper.insertMateriel(materiel);
	}

	@Override
	public void delMateriel(String materielId) {
		mapper.delMateriel(materielId);
	}

	@Override
	public int modifyMateriel(Materiel materiel) {
		return mapper.updateMateriel(materiel);
	}

	@Override
	public List<Materiel> adminGetMateriel(String code) {
		return mapper.adminGetMateriel(code);
	}

	@Override
	public List<Materiel> getMaterielByName(String name) {
		return mapper.queryMaterielByName(name);
	}

    @Override
    public Integer selectMaterielNum() {

		return mapper.selectMaterielNum();
    }


}