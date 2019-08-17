package com.rs.teach.service.materiel;

import java.util.List;

import com.rs.teach.mapper.materiel.entity.Materiel;

public interface MaterielService{
	
	public List<Materiel> getMateriel(String adminOperation);
	
	public int modifyStatus(String materielId, String status);
	
	public Materiel getMaterielById(String materielId);
	
	public int addMateriel(Materiel materiel);
}