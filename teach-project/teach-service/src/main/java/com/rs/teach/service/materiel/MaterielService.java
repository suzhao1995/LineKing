package com.rs.teach.service.materiel;

import java.util.List;

import com.rs.teach.mapper.materiel.entity.Materiel;

public interface MaterielService{
	
	public List<Materiel> getMateriel(String adminOperation,String code);
	
	public int modifyStatus(String materielId, String status);
	
	public Materiel getMaterielById(String materielId);
	
	public int addMateriel(Materiel materiel);
	
	public void delMateriel(String materielId);
	
	public int modifyMateriel(Materiel materiel);
	
	public List<Materiel> adminGetMateriel(String code, String searchName);
	
	public List<Materiel> getMaterielByName(String name);

    Integer selectMaterielNum();
}