package com.rs.teach.controller.backstage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.BPUtil;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.backstage.entity.AnswerSheet;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.backstage.entity.TrainDataAnswer;
import com.rs.teach.mapper.backstage.entity.UserTrainDataRela;
import com.rs.teach.mapper.backstage.vo.AnswerSheetVo;
import com.rs.teach.mapper.backstage.vo.TrainDataAndAnswerVo;
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
    private AnswerSheetService answerSheetService;

    /**
     * 培训考核文件上传
     *
     * @param trainDataFile 为考核文件
     * @param answerFile    答案文件
     * @param trainData
     * @return
     */
    @RequestMapping(value = "/trainDataUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataUpload(@RequestParam(value = "trainDataFile", required = false) MultipartFile trainDataFile,
                                        @RequestParam(value = "answerFile", required = false) MultipartFile answerFile,
                                        TrainData trainData) {
        ResponseBean bean = new ResponseBean();

        if (trainDataFile.isEmpty() || answerFile.isEmpty()) {
            bean.addError(ResponseBean.CODE_NOTUPLOAD_ERROR, "未上传文件");
            return bean;
        }
        //考核文件上传
        Map<String, Object> trainDataMap = FileUpDownUtil.trainDataUpload(trainDataFile);
        if (!(trainDataMap != null && "0".equals(trainDataMap.get("code")))) {
            bean.addError(ResponseBean.CODE_FILE_ERROR, trainDataMap.get("message").toString());
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
        Map<String, Object> answerMap = FileUpDownUtil.trainDataUpload(answerFile);
        if (!(answerMap != null && "0".equals(answerMap.get("code")))) {
            bean.addError(ResponseBean.CODE_FILE_ERROR, answerMap.get("message").toString());
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

        try {
            //添加考核系列文件（考核文件添加，考核文件答案添加，考核人员与考核文件关联表添加）
            trainDataService.addTrainDataAll(trainData, trainDataAnswer);

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
     * 培训考核文件修改回显
     *
     * @param trainData(只取主键id) pageDto
     * @return
     */
    @RequestMapping(value = "/querytrainDataAndAnswer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean querytrainDataAndAnswer(@RequestBody TrainData trainData) {
        ResponseBean bean = new ResponseBean();
        List<TrainDataAndAnswerVo> vo = trainDataService.queryTrainDataAndAnswer(trainData.getId());
        bean.addSuccess(vo);
        return bean;
    }

    /**
     * 培训考核文件修改
     *
     * @param trainDataFile  为考核文件
     * @param answerFile     答案文件
     * @param trainData（id必传 trainDataName必须  trainCourseId,answerId）
     * @return
     */
    @RequestMapping(value = "/trainDataUpdate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataUpdate(@RequestParam(value = "trainDataFile", required = false) MultipartFile trainDataFile,
                                        @RequestParam(value = "answerFile", required = false) MultipartFile answerFile,
                                        TrainData trainData) {
        ResponseBean bean = new ResponseBean();
        //答案表数据
        TrainDataAnswer trainDataAnswer = new TrainDataAnswer();
        TrainDataAnswer answerVo = new TrainDataAnswer();
        TrainData trainDataVo = new TrainData();

        if (trainDataFile != null) {
            //考核文件上传
            Map<String, Object> map = FileUpDownUtil.trainDataUpload(trainDataFile);
            if (!(map != null && "0".equals(map.get("code")))) {
                bean.addError(ResponseBean.CODE_FILE_ERROR, map.get("message").toString());
                return bean;
            }
            trainData.setTrainDataId(map.get("trainDataId").toString());
            trainData.setTrainDataFileName(map.get("trainDataFileName").toString());
            trainData.setTrainDataUrl(map.get("trainDataUrl").toString());
            trainData.setTrainDataPath(map.get("trainDataPath").toString());
            trainData.setTrainDataType(map.get("trainDataType").toString());
            //获取之前培训考核文件路径
            trainDataVo = trainDataService.selectTrainDataById(trainData.getId());
        }

        if (answerFile != null) {
            //答案文件上传
            Map<String, Object> answerMap = FileUpDownUtil.trainDataUpload(answerFile);
            if (!(answerMap != null && "0".equals(answerMap.get("code")))) {
                bean.addError(ResponseBean.CODE_FILE_ERROR, answerMap.get("message").toString());
                return bean;
            }
            trainDataAnswer.setAnswerId(trainData.getAnswerId());
            trainDataAnswer.setAnswerFileId(answerMap.get("trainDataId").toString());
            trainDataAnswer.setTrainAnswerName(trainData.getTrainDataName());
            trainDataAnswer.setTrainAnswerPath(answerMap.get("trainDataPath").toString());
            trainDataAnswer.setTrainAnswerType(answerMap.get("trainDataType").toString());
            trainDataAnswer.setTrainAnswerUrl(answerMap.get("trainDataUrl").toString());
            trainDataAnswer.setTrainDataFileName(answerMap.get("trainDataFileName").toString());
            //获取之前答案文件信息根据answerId
            answerVo = trainDataAnswerService.selectTrainDataAnswerByAnswerId(trainData.getAnswerId());
        }
        try {
            String[] fileNames = {trainDataVo.getTrainDataUrl(), answerVo.getTrainAnswerUrl()};
            trainDataService.UpdateTrainData(trainData, trainDataAnswer);
            log.info("培训考核文件-修改-成功");
            //删除修改前的文件
            DeleteFileUtil.deleteFiles(fileNames);
            bean.addSuccess("失败");
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
            if(vo != null){
                fileNames.add(vo.getTrainAnswerUrl());
                fileNames.add(vo.getTrainDataUrl());
                if (CollUtil.isNotEmpty(vo.getList())) {
                    for (TrainDataAnswer tvo : vo.getList()) {
                        fileNames.add(tvo.getTrainAnswerUrl());
                    }
                }
            }
            trainDataService.trainDataDelete(trainData.getId());
            DeleteFileUtil.deleteFiles(fileNames);

            log.info("培训考核文件-删除-成功");
            bean.addSuccess("成功");
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
        //trainData的主键Id
        String id = request.getParameter("id");
        TrainDataAnswer trainDataAnswer = trainDataAnswerService.selectTrainDataAnswer(id);
        try {
            Map<String, Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, trainDataAnswer.getTrainAnswerUrl(), trainDataAnswer.getTrainAnswerType(), trainDataAnswer.getTrainDataFileName());
            if (resultMap != null && "0".equals(resultMap.get("code"))) {
                log.info("下载答案-成功");
                bean.addSuccess("失败");
            } else {
                bean.addError(ResponseBean.CODE_DOWNLOAD_ERROR, resultMap.get("message").toString());
                log.error(ResponseBean.CODE_DOWNLOAD_ERROR);
            }
        } catch (IOException e) {
            log.error("下载答案-失败", e);
            bean.addError("系统异常");
        }
        return bean;
    }


    /**
     * 分页查询人员考核信息   即答卷
     *
     * @param trainData(只取id) pageDto
     * @return
     */
    @RequestMapping(value = "/queryAnswerSheet", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean queryAnswerSheet(@RequestBody TrainData trainData) {
        ResponseBean bean = new ResponseBean();
        //根据培训考核文件表主键查询考核人员上传答卷信息
        PageInfo<AnswerSheetVo> pageInfo = PageHelper.startPage(trainData).doSelectPageInfo(() -> answerSheetService.queryAnswerSheet(trainData));
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
                bean.addSuccess("成功");
            } else {
                bean.addError(ResponseBean.CODE_DOWNLOAD_ERROR, resultMap.get("message").toString());
                log.error(ResponseBean.CODE_DOWNLOAD_ERROR);
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
    @RequestMapping(value = "/deleteAnswerSheetById", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean deleteAnswerSheetById(@RequestBody AnswerSheet answerSheet) {
        ResponseBean bean = new ResponseBean();
        try {
            BPUtil.check(StrUtil.isBlank(answerSheet.getAnswerSheetId()), "没有答卷id");
            AnswerSheet vo = answerSheetService.selectAnswerSheet(answerSheet.getAnswerSheetId());
            String fileName = vo.getTrainSheetUrl();
            answerSheetService.deleteAnswerSheetById(answerSheet.getAnswerSheetId());
            DeleteFileUtil.deleteFile(fileName);
            bean.addSuccess("删除成功");
            log.info("根据id删除答卷表数据-删除-成功");
        } catch (Exception e) {
            log.error("根据id删除答卷表数据-删除-失败");
            bean.addError("失败");
        }
        return bean;
    }

}
