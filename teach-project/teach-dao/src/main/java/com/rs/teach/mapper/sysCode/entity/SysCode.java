package com.rs.teach.mapper.sysCode.entity;

import java.io.Serializable;

/**
* SysCode.java
* @Description:字典实体类
* @author: suzhao
* @date: 2019年8月27日 下午6:19:38
* @version: V1.0
*/
public class SysCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -955652327759764999L;
	
	private String cid;	// id
	
	private String code;	//code
	
	private String codeValue;	//code对应的值
	
	private String codeType;	//code类型
	
	private String codeSort;	//排序
	
	private String createBy;	//创建人
	
	private String createDate;	//创建时间

	public SysCode() {
		
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeSort() {
		return codeSort;
	}

	public void setCodeSort(String codeSort) {
		this.codeSort = codeSort;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
	
}