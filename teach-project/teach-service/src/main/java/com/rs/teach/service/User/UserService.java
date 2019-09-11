package com.rs.teach.service.User;

import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;
import com.rs.teach.mapper.user.entity.User;

import java.util.List;

/**
* UserService.java
* @Description:用户service
* @author: suzhao
* @date: 2019年7月22日 下午4:59:28
* @version: V1.0
*/
public interface UserService {
	public String test();
	
	/**
	* 根据ID查询用户信息
	* @param id用户名
	* @throws
	* @return user 
	* @author suzhao
	* @date 2019年7月22日 下午5:00:49
	*/
	public User getUserById(String id);
	
	/**
	* 用户是否修改过个人信息
	* @param id
	* @throws
	* @return boolean
	* @author suzhao
	* @date 2019年7月24日 下午3:46:41
	*/
	public boolean isModifyInfo(String id);
	
	/**
	* 修改用户信息
	* @param 
	* @throws
	* @return int
	* @author suzhao
	* @date 2019年8月2日 下午3:03:15
	*/
	public int modifyUser(User user);

	/**
	 * 添加普通用户
	 * @param user
	 * @param picAttr
	 */
	void addUserAndPic(User user, PicAttr picAttr);

	/**
	 * 校验
	 * @param userId
	 * @return
	 */
	Integer checkUserId(String userId);
	/**
	 * 校验
	 * @param serialNumber
	 * @return
	 */
	Integer checkTelNum(String serialNumber);

	/**
	 * 查询所有用户信息
	 * @return
	 */
	List<User> selectUserInfo(User user);

	/**
	 * 修改用户信息
	 * @param user
	 * @param picAttr
	 */
	int updateUserInfoAndPic(User user, PicAttr picAttr);

	/**
	 * 删除用户
	 * @param userId
	 */
	void deleteUser(String userId);

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
	List<User> selectTeachBySchoolId(String schoolId);

	/**
	 * 修改用户信息回显
	 * @param userId
	 * @return
	 */
    User selectUserInfoById(String userId);
}
