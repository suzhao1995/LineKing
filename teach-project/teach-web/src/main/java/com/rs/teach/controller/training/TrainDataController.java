package com.rs.teach.controller.training;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.backstage.entity.AnswerSheet;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.mapper.common.OptionVo;
import com.rs.teach.mapper.common.PageDto;
import com.rs.teach.service.backstage.AnswerSheetService;
import com.rs.teach.service.backstage.TrainDataService;
import com.rs.teach.service.backstage.UserTrainDataRelaService;
import com.rs.teach.service.training.TrainCourseService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 汪航
 * @Description 培训考核
 * @create 2019-08-28 17:56
 */
@Slf4j
@Controller
@RequestMapping("/trainData")
public class TrainDataController {

    @Autowired
    private TrainDataService trainDataService;

    @Autowired
    private TrainCourseService trainCourseService;
    @Autowired
    private AnswerSheetService answerSheetService;
    @Autowired
    private UserTrainDataRelaService userTrainDataRelaService;


    /**
     * 查询所有培训考核文件
     *
     * @param pageDto
     * @return
     */
    @RequestMapping(value = "/trainDataSelect", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataSelect(@RequestBody PageDto pageDto) {
        ResponseBean bean = new ResponseBean();
        PageInfo<TrainData> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() -> trainDataService.selectTrainData());
        bean.addSuccess(pageInfo);
        return bean;
    }

    /**
     * 下载培训考核文件
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/trainDataDownload", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean trainDataDownload(HttpServletRequest request, HttpServletResponse response) {
        ResponseBean bean = new ResponseBean();
        //trainData的主键Id
        String id = request.getParameter("id");
        String trainCourseId = request.getParameter("trainCourseId");
        String userid = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        //是否有考核权限
        Integer count = userTrainDataRelaService.isEmpty(id, trainCourseId, userid);
        if (count == 0) {
            bean.addError("对不起！您暂时不能参见考核！");
            return bean;
        }
        TrainData trainData = trainDataService.selectTrainDataById(id);
        try {
            Map<String, Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, trainData.getTrainDataUrl(), trainData.getTrainDataType(), trainData.getTrainDataName());
            if (resultMap != null && "0".equals(resultMap.get("code"))) {
                log.info("培训考核文件下载-成功");
                bean.addSuccess();
            } else {
                bean.addError(resultMap.get("message").toString());
            }
        } catch (IOException e) {
            log.error("培训考核文件下载-失败", e);
            bean.addError("系统异常");
        }
        return bean;
    }

    /**
     * 考核课程下拉列表
     *
     * @return
     */
    @RequestMapping(value = "/trainCourseList", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainCourseList() {
        ResponseBean bean = new ResponseBean();
        List<OptionVo> list = trainCourseService.trainCourseList();
        bean.addSuccess(list);
        return bean;
    }

    /**
     * 用户上传答卷
     *
     * @param file (id: trainData的id主键，trainCourseId培训课程,file)
     * @return
     */
    @RequestMapping(value = "/trainDataAnswerUpLoad", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataAnswerUpLoad(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        AnswerSheet answerSheet = new AnswerSheet();
        String id = request.getParameter("id");
        String trainCourseId = request.getParameter("trainCourseId");
        String userid = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        //是否有考核权限
        Integer count = userTrainDataRelaService.isEmpty(id, trainCourseId, userid);
        if (count == 0) {
            bean.addError("对不起！您暂时不能参见考核！");
            return bean;
        }
        if (file != null) {
            if (!file.isEmpty()) {
                Map<String, Object> map = FileUpDownUtil.trainDataUpload(file);
                if (!(map != null && "0".equals(map.get("code")))) {
                    bean.addError(map.get("message").toString());
                    return bean;
                }
                answerSheet.setUserId(userid);
                answerSheet.setTrainSheetId(map.get("trainDataId").toString());
                answerSheet.setTrainSheetFileName(map.get("trainDataFileName").toString());
                answerSheet.setTrainSheetUrl(map.get("trainDataUrl").toString());
                answerSheet.setTrainSheetPath(map.get("trainDataPath").toString());
                answerSheet.setTrainSheetType(map.get("trainDataType").toString());
                answerSheet.setAddTime(DateUtil.now());
                answerSheet.setEvaluationId(DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN));
                answerSheet.setTrainCourseId(trainCourseId);
            }
        }
        try {
            //添加答卷表,评价表和修改考核人员与考核文件关联表的ANSWER_SHEET_ID
            answerSheetService.addAnswerSheet(answerSheet, id);
            bean.addSuccess();
            log.info("培训考核-用户上传答卷-成功");
        } catch (Exception e) {
            bean.addError("失败");
            log.error("培训考核-用户上传答卷-失败");
            DeleteFileUtil.deleteFile(answerSheet.getTrainSheetUrl());
        }
        return bean;
    }
}
