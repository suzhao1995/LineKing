package com.rs.teach.controller.backstage;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.backstage.entity.AnswerSheet;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.mapper.common.ConditionExtVo;
import com.rs.teach.service.User.UserService;
import com.rs.teach.service.backstage.AnswerSheetService;
import com.rs.teach.service.backstage.TrainDataService;
import com.rs.teach.service.backstage.UserTrainDataRelaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wanghang
 * @Description 考核人员与考核文件关联controller
 * @create 2019-09-06 11:46
 */
@Slf4j
@Controller
@RequestMapping("/beforeUserTrainDataRela")
public class BeforeUserTrainDataRela {

    @Autowired
    private UserTrainDataRelaService userTrainDataRelaService;
    @Autowired
    private TrainDataService trainDataService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnswerSheetService answerSheetService;

    /**
     * 分页查询参与人员
     *
     * @param trainData(取主键id) pageDto
     * @return
     */
    @RequestMapping(value = "/queryUserTrainDataRela", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean queryUserTrainDataRela(@RequestBody TrainData trainData) {
        ResponseBean bean = new ResponseBean();
        //根据培训考核文件表主键查询考核人员信息
        PageInfo<UserTrainDataRela> pageInfo = PageHelper.startPage(trainData).doSelectPageInfo(() -> userTrainDataRelaService.queryUserTrainDataRela(trainData));
        bean.addSuccess(pageInfo);
        return bean;
    }


    /**
     * 考核科目回显（考核文件表存在的科目）
     */
    @RequestMapping(value = "/queryTrainDataCourseId")
    @ResponseBody
    public ResponseBean queryTrainDataCourseId() {
        ResponseBean bean = new ResponseBean();
        List<TrainData> vo = trainDataService.queryTrainDataCourseId();
        bean.addSuccess(vo);
        return bean;
    }

    /**
     * 回显参与考核人员（数组）
     *
     * @return
     */
    @RequestMapping(value = "/echoPeople", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean echoPeople(@RequestBody UserTrainDataRela UserTrainDataRela) {
        ResponseBean bean = new ResponseBean();
        String[] people = userTrainDataRelaService.echoPeople(UserTrainDataRela);
        bean.addSuccess(people);
        return bean;
    }


    /**
     * 考核人员树状图
     */
    @RequestMapping(value = "/queryConditionExtVo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean queryConditionExtVo() {
        ResponseBean bean = new ResponseBean();
        List<ConditionExtVo> list = userService.queryOptionVo();
        bean.addSuccess(list);
        return bean;
    }


    /**
     * 添加参与人员
     *
     * @param userTrainDataRela(userIds,trainCourseId,dataId)
     * @return
     */
    @RequestMapping(value = "/addUserTrainData", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addUserTrainData(@RequestBody UserTrainDataRela userTrainDataRela, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        //指派人id
        String adminId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        userTrainDataRela.setAdminId(adminId);
        try {
            if (ArrayUtil.isNotEmpty(userTrainDataRela.getUserIds())) {
                userTrainDataRelaService.addUserTrainData(userTrainDataRela);
            }else {
                bean.addError(ResponseBean.CODE_MESSAGE_ERROR,"请选择参与人员");
            }
            bean.addSuccess();
            log.info("添加参与人员-成功");
        } catch (Exception e) {
            bean.addError("失败");
            log.error("添加参与人员-失败");
        }
        return bean;
    }


    /**
     * 删除参与人员
     *
     * @param userTrainDataRela(主键id,answerSheetId)
     * @return
     */
    @RequestMapping(value = "/deleteUserTrainDataRela", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean queryAnswerSheet(@RequestBody UserTrainDataRela userTrainDataRela) {
        ResponseBean bean = new ResponseBean();
        AnswerSheet answerSheet = new AnswerSheet();
        if (!StrUtil.equalsIgnoreCase("0", userTrainDataRela.getAnswerSheetId())) {
            answerSheet = answerSheetService.selectAnswerSheet(userTrainDataRela.getAnswerSheetId());
        }
        try {
            userTrainDataRelaService.deleteUserTrainDataRela(userTrainDataRela);
            bean.addSuccess("成功");
            log.info("删除参与人员-成功");
            DeleteFileUtil.deleteFile(answerSheet.getTrainSheetUrl());
        } catch (Exception e) {
            bean.addError("失败");
            log.error("删除参与人员-失败");
        }
        return bean;
    }

}
