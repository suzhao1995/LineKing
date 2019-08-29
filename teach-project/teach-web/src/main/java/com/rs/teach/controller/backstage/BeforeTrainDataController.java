package com.rs.teach.controller.backstage;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.rs.common.utils.DeleteFileUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseSubject;
import com.rs.teach.service.backstage.TrainDataService;
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

import java.util.List;
import java.util.Map;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-28 12:14
 */
@Slf4j
@Controller
@RequestMapping("/beforeTrainData")
public class BeforeTrainDataController {

    @Autowired
    private TrainDataService trainDataService;

    @Autowired
    private TrainCourseService trainCourseService;
    /**
     * 培训考核文件上传
     * @param trainDataFile
     * @return
     */
    @RequestMapping(value = "/trainDataUpload",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataUpload(@RequestParam(value = "trainDataFile",required = false) MultipartFile trainDataFile,TrainData trainData){
        ResponseBean bean = new ResponseBean();
        if (trainDataFile == null) {
            if (trainDataFile.isEmpty()) {
                bean.addError("文件为空");
                return bean;
            }
        }
        Map<String, Object> map = FileUpDownUtil.trainDataUpload(trainDataFile);
        if (!(map != null && "0".equals(map.get("code")))) {
            bean.addError(map.get("message").toString());
            return bean;
        }
        //生成时间id，例如：20190829142012  DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN)
        trainData.setId(DateUtil.format(DateTime.now(), DatePattern.PURE_DATETIME_PATTERN));
        trainData.setTrainDataId(map.get("trainDataId").toString());
        trainData.setTrainDataFileName(map.get("trainDataFileName").toString());
        trainData.setTrainDataUrl(map.get("trainDataUrl").toString());
        trainData.setTrainDataPath(map.get("trainDataPath").toString());
        trainData.setTrainDataType(map.get("trainDataType").toString());
        trainData.setAddTime(DateUtil.now());
        try {
            trainDataService.addTrainData(trainData);
            log.info("培训考核文件-添加-成功");
            bean.addSuccess();
        } catch (Exception e) {
            log.error("培训考核文件-添加-失败",e);
            bean.addError("失败");
        }
        return bean;
    }

    /**
     * 培训考核文件修改
     * @param trainDataFile（非必须）
     * @param trainData（id必传   trainDataName非必须）
     * @return
     */
    @RequestMapping(value = "/trainDataUpdate",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataUpdate(@RequestParam(value = "trainDataFile",required = false) MultipartFile trainDataFile,TrainData trainData){
        ResponseBean bean = new ResponseBean();
        if (trainDataFile != null) {
            if (!trainDataFile.isEmpty()) {
                Map<String, Object> map = FileUpDownUtil.trainDataUpload(trainDataFile);
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
            log.error("培训考核文件-修改-失败",e);
            bean.addError("失败");
        }
        return bean;
    }

    /**
     * 删除培训考核文件
     * @param trainData
     * @return
     */
    @RequestMapping(value = "/trainDataDelete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataDelete(@RequestBody TrainData trainData){
        ResponseBean bean = new ResponseBean();
        try {
            trainDataService.trainDataSelect(trainData.getId());
            log.info("培训考核文件-删除-成功");
            bean.addSuccess();
        } catch (Exception e) {
            log.error("培训考核文件-删除-失败");
            bean.addError("失败");
        }
        return bean;
    }

    /**
     * 查询所有培训课程科目
     * @return
     */
    @RequestMapping(value = "/trainCourseSubjectSelect",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainCourseSubjectSelect(){
        ResponseBean bean = new ResponseBean();
        List<TrainCourseSubject> list= trainCourseService.selectTrainCourseSubject();
        bean.addSuccess(list);
        return bean;
    }
}
