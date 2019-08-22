package com.rs.teach.mapper.materiel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rs.teach.mapper.materiel.entity.Materiel;

public interface MaterielMapper{
	List<Materiel> queryMateriel(@Param("adminOperation") String adminOperation, @Param("code") String code);
	
	int updateStatus(@Param("materielId") String materielId, @Param("status") String status);
	
	Materiel queryMaterielById(String materielId);
	
	int insertMateriel(Materiel materiel);
	
}