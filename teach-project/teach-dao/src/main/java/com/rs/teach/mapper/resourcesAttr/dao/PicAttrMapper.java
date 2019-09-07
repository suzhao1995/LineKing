package com.rs.teach.mapper.resourcesAttr.dao;

import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;
import org.apache.ibatis.annotations.Param;

/**
* PicAttrMapper.java
* @Description:图片属性mapper接口
* @author: suzhao
* @date: 2019年8月2日 下午3:22:00
* @version: V1.0
*/
public interface PicAttrMapper{
	/**
	* 修改图片属性资源
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:25:54
	*/
	public int updatePic(PicAttr pic);
	
	/**
	* 添加图片属性资源
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:26:25
	*/
	public int insertPic(PicAttr pic);
	
	/**
	* 查询图片属性
	* @param 
	* @throws
	* @return PicAttr
	* @author suzhao
	* @date 2019年8月2日 下午3:34:11
	*/
	 PicAttr queryPic(@Param("userId") String userId);

	/**
	 * 删除头像
	 * @param userId
	 */
	void deletePic(String userId);
}