package com.rs.teach.controller.training;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.backstage.entity.TrainData;
import com.rs.teach.mapper.common.PageDto;
import com.rs.teach.service.backstage.TrainDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-28 17:56
 */
@Slf4j
@Controller
@RequestMapping("/trainData")
public class TrainDataController {

    @Autowired
    private TrainDataService trainDataService;

    /**
     * 查询所有培训考核文件
     * @param pageDto
     * @return
     */
    @RequestMapping(value = "/trainDataSelect",method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean trainDataSelect(@RequestBody PageDto pageDto){
        ResponseBean bean = new ResponseBean();
        PageInfo<TrainData> pageInfo = PageHelper.startPage(pageDto).doSelectPageInfo(() -> trainDataService.selectTrainData());
        bean.addSuccess(pageInfo);
        return bean;
    }

    /**
     * 下载培训考核文件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/trainDataDownload",method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean trainDataDownload(HttpServletRequest request, HttpServletResponse response){
        ResponseBean bean = new ResponseBean();
        String id = request.getParameter("id");
        TrainData trainData = trainDataService.selectTrainDataById(id);
        try {
            Map<String, Object> resultMap = FileUpDownUtil.fileDownLoad(request, response, trainData.getTrainDataUrl(), trainData.getTrainDataType(), trainData.getTrainDataFileName());
            if(resultMap != null && "0".equals(resultMap.get("code"))){
                log.info("培训考核文件下载-成功");
                bean.addSuccess();
            }else{
                bean.addError(resultMap.get("message").toString());
            }
        } catch (IOException e) {
            log.error("培训考核文件下载-失败", e);
            bean.addError("系统异常");
        }
        return bean;
    }
}
