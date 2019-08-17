package com.rs.teach.mapper.materiel.entity;

import java.io.Serializable;

public class Materiel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -783073642014179290L;
	
	private String materielId;	//物料id
	
	private String materielName;	//物料名字
	
	private String materielUrl;	//物料url
	
	private String materielStatus;		//物料上下架状态	0:下架；1上架
	
	private String materielDetail;		//物料说明
	
	public Materiel (){
		
	}

	public String getMaterielId() {
		return materielId;
	}

	public void setMaterielId(String materielId) {
		this.materielId = materielId;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielUrl() {
		return materielUrl;
	}

	public void setMaterielUrl(String materielUrl) {
		this.materielUrl = materielUrl;
	}

	public String getMaterielStatus() {
		return materielStatus;
	}

	public void setMaterielStatus(String materielStatus) {
		this.materielStatus = materielStatus;
	}

	public String getMaterielDetail() {
		return materielDetail;
	}

	public void setMaterielDetail(String materielDetail) {
		this.materielDetail = materielDetail;
	}
	
	
}