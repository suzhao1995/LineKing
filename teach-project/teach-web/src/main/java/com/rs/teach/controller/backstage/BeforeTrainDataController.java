package com.rs.teach.controller.backstage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.backstage.entity.AnswerSheet;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.mapper.backstage.vo.AnswerSheetVo;
import com.rs.teach.mapper.backstage.vo.TrainDataFileAllUrlVo;
import com.rs.teach.service.backstage.AnswerSheetService;
import com.rs.teach.service.backstage.TrainDataAnswerService;
import com.rs.teach.service.backstage.TrainDataService;
import com.rs.teach.service.backstage.UserTrainDataRelaService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 汪航
 * @Description 管理员--培训考核
 * @create 2019-08-28 12:14
 */
@Slf4j
@Controller
@RequestMapping("/beforeTrainData")
public class BeforeTrainDataController {

    @Autowired
    private TrainDataService trainDataService;

    @Autowired
    private TrainDataAnswerService trainDataAnswerService;
    @Autowired
    private UserTrainDataRelaService userTrainDataRelaService;
    @Autowired
    private AnswerSheetService answerSheetService;

    /**
     * 培训考核文件上传
     *
     * @param files
     * @return
     */
    @RequestMapping(value = "/trainDataUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataUpload(@RequestParam(value = "files", required = false) MultipartFile[] files, TrainData trainData, HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        //指派人id
        String adminId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();

        if (ArrayUtil.isEmpty(files)) {
            if (files[0].isEmpty() || files[1].isEmpty()) {
                bean.addError("文件为空");
                return bean;
            }
        }
        //考核文件上传
        Map<String, Object> trainDataMap = FileUpDownUtil.trainDataUpload(files[0]);
        if (!(trainDataMap != null && "0".equals(trainDataMap.get("code")))) {
            bean.addError(trainDataMap.get("message").toString());
            return bean;
        }
        //生成时间id，例如：20190829142012  DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN)
        trainData.setId(DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN));
        trainData.setTrainDataId(trainDataMap.get("trainDataId").toString());
        trainData.setTrainDataFileName(trainDataMap.get("trainDataFileName").toString());
        trainData.setTrainDataUrl(trainDataMap.get("trainDataUrl").toString());
        trainData.setTrainDataPath(trainDataMap.get("trainDataPath").toString());
        trainData.setTrainDataType(trainDataMap.get("trainDataType").toString());
        trainData.setAddTime(DateUtil.now());
        trainData.setAnswerId(DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN));

        //答案文件上传
        Map<String, Object> answerMap = FileUpDownUtil.trainDataUpload(files[0]);
        if (!(answerMap != null && "0".equals(answerMap.get("code")))) {
            bean.addError(answerMap.get("message").toString());
            return bean;
        }
        TrainDataAnswer trainDataAnswer = new TrainDataAnswer();
        trainDataAnswer.setAddTime(DateUtil.now());
        trainDataAnswer.setAnswerId(trainData.getAnswerId());
        trainDataAnswer.setAnswerFileId(answerMap.get("trainDataId").toString());
        trainDataAnswer.setTrainAnswerName(trainData.getTrainDataName());
        trainDataAnswer.setTrainAnswerPath(answerMap.get("trainDataPath").toString());
        trainDataAnswer.setTrainAnswerType(answerMap.get("trainDataType").toString());
        trainDataAnswer.setTrainAnswerUrl(answerMap.get("trainDataUrl").toString());
        trainDataAnswer.setTrainDataFileName(answerMap.get("trainDataFileName").toString());

        //考核人员与考核文件关联表添加
        UserTrainDataRela userTrainDataRela = new UserTrainDataRela();
        userTrainDataRela.setAdminId(adminId);
        userTrainDataRela.setDataId(trainData.getId());
        userTrainDataRela.setUserIds(trainData.getUserIds());
        userTrainDataRela.setTrainCourseId(trainData.getTrainCourseId());
        try {
            //考核文件添加
            trainDataService.addTrainData(trainData);
            //考核文件答案添加
            trainDataAnswerService.addTrainDataAnswer(trainDataAnswer);
            //考核人员与考核文件关联表添加
            userTrainDataRelaService.addUserTrainData(userTrainDataRela);
            log.info("培训考核文件-添加-成功");
            bean.addSuccess();
        } catch (Exception e) {
            log.error("培训考核文件-添加-失败", e);
            bean.addError("失败");
            String[] fileNames = {trainData.getTrainDataUrl(), trainDataAnswer.getTrainAnswerUrl()};
            DeleteFileUtil.deleteFiles(fileNames);
        }
        return bean;
    }

    /**
     * 培训考核文件修改
     *
     * @param files（非必须）
     * @param trainData（id必传 trainDataName非必须）
     * @return
     */
    @RequestMapping(value = "/trainDataUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataUpdate(@RequestParam(value = "files", required = false) MultipartFile files, TrainData trainData) {
        ResponseBean bean = new ResponseBean();
        if (files != null) {
            if (!files.isEmpty()) {
                Map<String, Object> map = FileUpDownUtil.trainDataUpload(files);
                if (!(map != null && "0".equals(map.get("code")))) {
                    bean.addError(map.get("message").toString());
                    return bean;
                }
                trainData.setTrainDataId(map.get("trainDataId").toString());
                trainData.setTrainDataFileName(map.get("trainDataFileName").toString());
                trainData.setTrainDataUrl(map.get("trainDataUrl").toString());
                trainData.setTrainDataPath(map.get("trainDataPath").toString());
                trainData.setTrainDataType(map.get("trainDataType").toString());

                //获取之前培训考核文件路径
                TrainData vo = trainDataService.selectTrainDataById(trainData.getId());
                DeleteFileUtil.deleteFile(vo.getTrainDataUrl());
            }
        }
        try {
            trainDataService.UpdateTrainData(trainData);
            log.info("培训考核文件-修改-成功");
            bean.addSuccess();
        } catch (Exception e) {
            log.error("培训考核文件-修改-失败", e);
            bean.addError("失败");
        }
        return bean;
    }

    /**
     * 删除培训考核文件
     *
     * @param trainData
     * @return
     */
    @RequestMapping(value = "/trainDataDelete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataDelete(@RequestBody TrainData trainData) {
        ResponseBean bean = new ResponseBean();
        ArrayList<String> fileNames = new ArrayList<>();
        try {
            //删除培训考核文件，返回文件所在服务器地址
            TrainDataFileAllUrlVo vo = trainDataService.selectFileAllUrl(trainData.getId());
            fileNames.add(vo.getTrainAnswerUrl());
            fileNames.add(vo.getTrainDataUrl());
            if (CollUtil.isNotEmpty(vo.getList())) {
                for (TrainDataAnswer tvo : vo.getList()) {
                    fileNames.add(tvo.getTrainAnswerUrl());
                }
            }
            trainDataService.trainDataDelete(trainData.getId());
            DeleteFileUtil.deleteFiles(fileNames);

            log.info("培训考核文件-删除-成功");
            bean.addSuccess();
        } catch (Exception e) {
            log.error("培训考核文件-删除-失败");
            bean.addError("失败");
        }
        return bean;
    }

    /**
     * 下载考核文件答案
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/trainDataAnswerDownload", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean trainDataAnswerDownload(HttpServletRequest request, HttpServletResponse response) {
        ResponseBean bean = new ResponseBean();
        String id = request.getParameter("id");
        TrainDataAnswer trainDataAnswer = trainDataAnswerService.selectTrainDataAnswer(id);
        try {
            Map<String, Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, trainDataAnswer.getTrainAnswerUrl(), trainDataAnswer.getTrainAnswerType(), trainDataAnswer.getTrainAnswerName());
            if (resultMap != null && "0".equals(resultMap.get("code"))) {
                log.info("下载用户考卷-成功");
                bean.addSuccess();
            } else {
                bean.addError(resultMap.get("message").toString());
            }
        } catch (IOException e) {
            log.error("下载用户考卷-失败", e);
            bean.addError("系统异常");
        }
        return bean;
    }


    /**
     * 分页查询答卷表
     *
     * @param trainData(只取id) pageDto
     * @return
     */
    @RequestMapping(value = "/queryAnswerSheet", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean queryAnswerSheet(@RequestBody TrainData trainData) {
        ResponseBean bean = new ResponseBean();
        //根据培训考核文件表主键查询考核人员上传答卷信息
        PageInfo<AnswerSheetVo> pageInfo = PageHelper.startPage(trainData).doSelectPageInfo(() -> answerSheetService.queryAnswerSheet(trainData.getId()));
        bean.addSuccess(pageInfo);
        return bean;
    }


    /**
     * 下载用户考卷
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/trainDataDownload", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean trainDataDownload(HttpServletRequest request, HttpServletResponse response) {
        ResponseBean bean = new ResponseBean();
        String answerSheetId = request.getParameter("answerSheetId");
        AnswerSheet answerSheet = answerSheetService.selectAnswerSheet(answerSheetId);
        try {
            Map<String, Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, answerSheet.getTrainSheetUrl(), answerSheet.getTrainSheetType(), answerSheet.getTrainSheetFileName());
            if (resultMap != null && "0".equals(resultMap.get("code"))) {
                log.info("下载用户考卷-成功");
                bean.addSuccess();
            } else {
                bean.addError(resultMap.get("message").toString());
            }
        } catch (IOException e) {
            log.error("下载用户考卷-失败", e);
            bean.addError("系统异常");
        }
        return bean;
    }

    /**
     * 根据id删除答卷表数据
     *
     * @param answerSheet(只取answerSheetId主键)
     * @return
     */
    @RequestMapping(value = "/deleteAnswerSheetById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean deleteAnswerSheetById(@RequestBody AnswerSheet answerSheet) {
        ResponseBean bean = new ResponseBean();
        try {
            AnswerSheet vo = answerSheetService.selectAnswerSheet(answerSheet.getAnswerSheetId());
            String fileName = vo.getTrainSheetUrl();
            answerSheetService.deleteAnswerSheetById(answerSheet.getAnswerSheetId());
            DeleteFileUtil.deleteFile(fileName);
        } catch (Exception e) {
            log.error("根据id删除答卷表数据-删除-失败");
            bean.addError("失败");
        }
        return bean;
    }

}
