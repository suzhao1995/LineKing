package com.rs.teach.controller.backstage;

import com.rs.common.utils.ResponseBean;
import com.rs.teach.service.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 汪航
 * @Description  后台video模块
 * @create 2019-08-21 16:47
 */
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
    public ResponseBean download(HttpServletRequest request, HttpServletResponse response ){
        ResponseBean bean = new ResponseBean();
        String videoSectionId = request.getParameter("videoSectionId");


        return bean;
    }
}
