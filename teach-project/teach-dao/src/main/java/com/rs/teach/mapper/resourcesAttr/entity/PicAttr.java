package com.rs.teach.mapper.resourcesAttr.entity;

import java.io.Serializable;

/**
* PicAttr.java
* @Description:图片属性
* @author: suzhao
* @date: 2019年7月24日 上午10:37:46
* @version: V1.0
*/
public class PicAttr implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2890922711538350841L;
	
	private String picId;	//图片id
	private String associationId;	//关联id
	private String picUrl;	//图片链接
	
	public PicAttr() {
		
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getAssociationId() {
		return associationId;
	}

	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	
}