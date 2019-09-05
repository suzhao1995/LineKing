package com.rs.teach.service.resourcesAttr;

import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;

public interface PicAttrService{
	/**
	* 修改图片属性资源
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:25:54
	*/
	 int modifyPic(PicAttr pic);
	
	/**
	* 添加图片属性资源
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:26:25
	*/
	 int addPic(PicAttr pic);
	
	/**
	* 查询图片属性
	* @param 
	* @throws
	* @return PicAttr
	* @author suzhao
	* @date 2019年8月2日 下午3:34:11
	*/
	 PicAttr getPic(String userId);
}