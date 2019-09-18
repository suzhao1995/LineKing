package com.rs.teach.mapper.user.dao;

import com.rs.teach.mapper.common.ConditionExtVo;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.mapper.user.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* UserMapper.java
* @Description:用户dao层接口
* @author: suzhao
* @date: 2019年7月23日 下午4:43:05
* @version: V1.0
*/
public interface UserMapper {
	/**
	* 根据userId关联图片资源表查询
	* @param id
	* @throws
	* @return user
	* @author suzhao
	* @date 2019年7月24日 下午3:48:33
	*/
	public User getUserById(String id);
	
	/**
	* 查询用户是否修改过个人信息
	* @param id
	* @throws 
	* @return int
	* @author suzhao
	* @date 2019年7月24日 下午3:50:04
	*/
	public int isUpdateInfo(String id);
	
	/**
	* 修改用户信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:04:00
	*/
	public int updateUser(User user);
	
	/**
	* 根据userId 查询用户表
	* @param 
	* @throws
	* @return User
	* @author suzhao
	* @date 2019年8月2日 下午4:26:48
	*/
	public User getTeachUser(String id);

	/**
	 * 查询此校区的老师数量
	 * @param schoolId
	 * @return
	 */
    Integer queryTeachNumBySchoolId(String schoolId);

	/**
	 * 校验
	 * @param userId
	 * @return
	 */
	Integer checkUserId(@Param("userId") String userId);

	/**
	 * 校验
	 * @param serialNumber
	 * @return
	 */
	Integer checkTelNum(@Param("serialNumber")String serialNumber);
	/**
	 * 添加用户
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 查询所有用户信息
	 * @param user
	 * @return
	 */
	List<User> selectUserInfo(User user);

	/**
	 * 管理员修改用户信息
	 * @param user
	 */
	void updateUserInfo(User user);

	/**
	 * 删除用户
	 * @param userId
	 */
	void deleteUser(@Param("userId")String userId);

	/**
	 * 考核人员回显（已添加的不会显）
	 * @return
	 */
    List<User> queryUserNotIn();
	/**
	 * 根据校区id查询所有教师
	 * @param schoolId
	 * @return
	 */
	List<User> selectTeachBySchoolId(@Param("schoolId") String schoolId);

	/**
	 * 修改用户信息回显
	 * @param userId
	 * @return
	 */
	UserVo selectUserInfoById(@Param("userId") String userId);

	/**
	 * 学校
	 * @return
	 */
	List<ConditionExtVo> schoolBy();

	/**
	 * 用户
	 * @param id
	 * @return
	 */
	List<ConditionExtVo> userBy(@Param("id")String id);
}
