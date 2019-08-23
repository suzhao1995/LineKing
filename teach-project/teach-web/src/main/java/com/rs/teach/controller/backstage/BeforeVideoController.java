package com.rs.teach.controller.backstage;

import cn.hutool.core.util.StrUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.section.entity.Section;
import com.rs.teach.mapper.video.entity.VideoSection;
import com.rs.teach.service.video.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 汪航
 * @Description  后台video模块
 * @create 2019-08-21 16:47
 */
@Slf4j
@Controller
@RequestMapping("/beforeVideo")
public class BeforeVideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 下载小章节video
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public ResponseBean download(HttpServletRequest request, HttpServletResponse response ){
        ResponseBean bean = new ResponseBean();
        String videoSectionId = request.getParameter("videoSectionId");
        try {
            VideoSection videoSection = videoService.selectVideoSection(videoSectionId);
            //下载视频文件
            Map<String, Object> resultMap = FileUpDownUtil.videoFileDownLoad(response, videoSection.getVideoSectionUrl(), videoSection.getVideoSectionName());

            bean.addSuccess("下载成功");
        } catch (IOException e) {
            log.error("视频资源下载异常",e);
            bean.addError("资源下载异常");
        }
        return bean;
    }

    /**
     * 下载当前video课程所有资源
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/videoAllDownload")
    @ResponseBody
    public ResponseBean videoAllDownload(HttpServletRequest request, HttpServletResponse response ){
        ResponseBean bean = new ResponseBean();
        String videoId = request.getParameter("videoId");
        String userId = UserInfoUtil.getUserInfo(request.getParameter("sessionKey")).get("userId").toString();
        List<Section> sections = videoService.queryVideoSectionByVideoId(videoId);
        return bean;
    }


}
