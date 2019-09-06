package com.rs.teach.controller.backstage;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.common.Enums.PermissionEnum;
import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.mapper.resourcesAttr.entity.PicAttr;
import com.rs.teach.mapper.user.entity.User;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.backstage.SchoolService;
import com.rs.teach.service.resourcesAttr.PicAttrService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @author 汪航
 * @Description 用户和管理员模块功能
 * @create 2019-09-02 17:44
 */
@Slf4j
@Controller
@RequestMapping("/beforeUser")
@Api(value = "/beforeUser", tags = "BeforeUserController", description = "用户和管理员模块功能")
public class BeforeUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private PicAttrService picAttrService;


    /**
     * userId校验
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/checkUserId", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean checkUserId(User user) {
        ResponseBean bean = new ResponseBean();
        if (userService.checkUserId(user.getUserId()) > 0) {
            bean.addError("登录账号已存在！请重新输入");
            return bean;
        }
        bean.addSuccess("成功");
        return bean;
    }

    /**
     * userId校验
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/checkTelNum", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean checkTelNum(User user) {
        ResponseBean bean = new ResponseBean();
        if (userService.checkTelNum(user.getSerialNumber()) > 0) {
            bean.addError("手机号不可重复绑定！请重新输入");
            return bean;
        }
        bean.addSuccess("成功");
        return bean;
    }


    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addUser(@RequestParam(value = "file", required = false) MultipartFile file, User user, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        PicAttr picAttr = new PicAttr();

        if (!file.isEmpty()) {
            //上传文件
            Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
            //文件上传是否成功
            if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
                bean.addError(resultMap.get("message").toString());
                return bean;
            }
            picAttr.setAssociationId(user.getUserId());
            picAttr.setPicUrl(resultMap.get("picUrl").toString());
            picAttr.setSavePath(resultMap.get("saveUrl").toString());
            picAttr.setPicId(resultMap.get("picId").toString());
        }
        //用户图像属性
        user.setAttr(picAttr);
        user.setAdminFlag(PermissionEnum.user_.getValue());
        user.setIsDefault("0");
        user.setStartTime(DateUtil.now());
        try {
            if (StrUtil.isNotBlank(picAttr.getPicId())) {
                picAttrService.addPic(picAttr);
            } else {
                bean.addError("头像上传失败");
                log.error("添加用户-头像上传失败");
                return bean;
            }
            userService.addUser(user);
            log.info("添加用户成功！");
            bean.addSuccess("添加成功!");
        } catch (Exception e) {
            log.error("用户管理员模块-添加用户-失败！", e);
            bean.addError("添加失败!");
            DeleteFileUtil.deleteFile(picAttr.getSavePath());
        }
        return bean;
    }


    /**
     * 学校下拉列表数据回显
     *
     * @return
     */
    @RequestMapping("/querySchool")
    @ResponseBody
    public ResponseBean querySchool() {
        ResponseBean bean = new ResponseBean();
        List<OptionVo> list = schoolService.selectSchool();
        bean.addSuccess(list);
        return bean;
    }


    /**
     * 查询所有用户信息
     *
     * @return
     */
    @RequestMapping(value = "/selectUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectUserInfo(@RequestBody User user) {
        ResponseBean bean = new ResponseBean();
        user.setAdminFlag(PermissionEnum.user_.getValue());
        List<User> list = userService.selectUserInfo(user);
        bean.addSuccess(list);
        return bean;
    }


    /**
     * 修改用户信息
     *
     * @return
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateUserInfo(@RequestParam(value = "file") MultipartFile file, User user, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        //修改人的userId
        String adminId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        user.setModifier(adminId);
        user.setUpdate(DateUtil.now());
        //获取用户之前图像本地路径（修改成功就删除）
        PicAttr pic = picAttrService.getPic(user.getUserId());
        PicAttr picAttr = new PicAttr();
        if (!file.isEmpty()) {
            //上传图片
            Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
            //图片上传是否成功
            if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
                bean.addError(resultMap.get("message").toString());
                return bean;
            }
            picAttr.setAssociationId(pic.getAssociationId());
            picAttr.setPicId(pic.getPicId());
            picAttr.setPicUrl(resultMap.get("picUrl").toString());
            picAttr.setSavePath(resultMap.get("saveUrl").toString());
        }
        try {
            if (StrUtil.isNotBlank(picAttr.getPicId())) {
                int i = picAttrService.modifyPic(picAttr);
                if (i == 1) {
                    //删除原始文件
                    DeleteFileUtil.deleteFile(pic.getSavePath());
                }
            }
            userService.updateUserInfo(user);
            bean.addSuccess("修改成功");
        } catch (Exception e) {
            log.error("用户管理员模块-修改用户-失败！", e);
            bean.addError("修改失败!");
            //删除新上传的图片
            DeleteFileUtil.deleteFile(picAttr.getSavePath());
        }
        return bean;
    }


    /**
     * 删除用户
     *
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean deleteUser(String userId) {
        ResponseBean bean = new ResponseBean();
        try {
            //获取用户之前图像本地路径
            PicAttr pic = picAttrService.getPic(userId);
            userService.deleteUser(userId);
            DeleteFileUtil.deleteFile(pic.getSavePath());
            bean.addSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * 查询所有管理员信息
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/selectAdminInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectAdminInfo(@RequestBody User user) {
        ResponseBean bean = new ResponseBean();
        if (StrUtil.isBlank(user.getAdminFlag())) {
            //查全部管理员
            user.setAdminFlag("3");
        }
        List<User> list = userService.selectUserInfo(user);
        bean.addSuccess(list);
        return bean;
    }


    /**
     * 权限下拉列表
     *
     * @return
     */
    @RequestMapping("/queryPermission")
    @ResponseBody
    public ResponseBean queryPermission() {
        ResponseBean bean = new ResponseBean();
        List<OptionVo> list = PermissionEnum.condition();
        bean.addSuccess(list);
        return bean;
    }


    /**
     * 新增管理员
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUserSupperAdmin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addUserSupperAdmin(@RequestParam(value = "file", required = false) MultipartFile file, User user, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        PicAttr picAttr = new PicAttr();

        if (!file.isEmpty()) {
            //上传文件
            Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
            //文件上传是否成功
            if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
                bean.addError(resultMap.get("message").toString());
                return bean;
            }
            picAttr.setAssociationId(user.getUserId());
            picAttr.setPicUrl(resultMap.get("picUrl").toString());
            picAttr.setSavePath(resultMap.get("saveUrl").toString());
            picAttr.setPicId(resultMap.get("picId").toString());
        }
        //用户图像属性
        user.setAdminFlag(PermissionEnum.admin.getValue());
        user.setAttr(picAttr);
        user.setIsDefault("0");
        user.setStartTime(DateUtil.now());
        try {
            if (StrUtil.isNotBlank(picAttr.getPicId())) {
                picAttrService.addPic(picAttr);
            } else {
                bean.addError("头像上传失败");
                log.error("添加管理员-头像上传失败");
                return bean;
            }
            userService.addUser(user);
            log.info("添加管理员成功！");
            bean.addSuccess("添加成功!");
        } catch (Exception e) {
            log.error("用户管理员模块-添加管理员-失败！", e);
            bean.addError("添加失败!");
            DeleteFileUtil.deleteFile(picAttr.getSavePath());
        }
        return bean;
    }

    /**
     * 修改管理员信息
     *
     * @return
     */
    @RequestMapping(value = "/updateUserInfoSupperAdmin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateUserInfoSupperAdmin(@RequestParam(value = "file") MultipartFile file, User user, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        //修改人的userId
        String adminId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        user.setModifier(adminId);
        user.setUpdate(DateUtil.now());
        //获取用户之前图像本地路径（修改成功就删除）
        PicAttr pic = picAttrService.getPic(user.getUserId());
        PicAttr picAttr = new PicAttr();
        if (!file.isEmpty()) {
            //上传图片
            Map<String, Object> resultMap = FileUpDownUtil.picUpLoad(request, file);
            //图片上传是否成功
            if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
                bean.addError(resultMap.get("message").toString());
                return bean;
            }
            picAttr.setAssociationId(pic.getAssociationId());
            picAttr.setPicId(pic.getPicId());
            picAttr.setPicUrl(resultMap.get("picUrl").toString());
            picAttr.setSavePath(resultMap.get("saveUrl").toString());
        }
        try {
            if (StrUtil.isNotBlank(picAttr.getPicId())) {
                int i = picAttrService.modifyPic(picAttr);
                if (i == 1) {
                    //删除原始文件
                    DeleteFileUtil.deleteFile(pic.getSavePath());
                }
            }
            userService.updateUserInfo(user);
            bean.addSuccess("修改成功");
        } catch (Exception e) {
            log.error("用户管理员模块-修改管理员-失败！", e);
            bean.addError("修改失败!");
            //删除新上传的图片
            DeleteFileUtil.deleteFile(picAttr.getSavePath());
        }
        return bean;
    }

    /**
     * 删除管理员
     *
     * @return
     */
    @RequestMapping(value = "/deleteUserSupperAdmin", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean deleteUserSupperAdmin(String userId) {
        ResponseBean bean = new ResponseBean();
        try {
            //获取用户之前图像本地路径
            PicAttr pic = picAttrService.getPic(userId);
            userService.deleteUser(userId);
            DeleteFileUtil.deleteFile(pic.getSavePath());
            bean.addSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }


}
