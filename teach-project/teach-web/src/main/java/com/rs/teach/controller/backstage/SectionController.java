package com.rs.teach.controller.backstage;

import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.service.section.SectionService;
import com.rs.teach.service.training.TrainSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-15 11:07
 */
@Controller
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TrainSectionService trainSectionService;


    /**
     * 添加章节
     * @param courseWareFile（课件）
     * @param practiceFile（作业）
     * @param testpaperFile（试卷）
     * @param sectionDto
     * @param request
     * @return
     */
    @RequestMapping(value = "/addSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addSection(@RequestParam("courseWareFile") MultipartFile courseWareFile,
                                   @RequestParam("practiceFile") MultipartFile practiceFile,
                                   @RequestParam("testpaperFile") MultipartFile testpaperFile,
                                   SectionDto sectionDto,
                                   HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        //文件上传
        Map<String, Object> resultMap = FileUpDownUtil.fileUpLoad(request, courseWareFile);
        //文件上传是否成功
        if (!(resultMap != null && "0".equals(resultMap.get("code")))) {
            bean.addError(resultMap.get("message").toString());
            return bean;
        }

        return bean;
    }


}
