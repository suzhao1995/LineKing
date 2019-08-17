package com.rs.teach.controller.backstage;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.rs.common.utils.FileUpDownUtil;
import com.rs.common.utils.ResponseBean;
import com.rs.teach.mapper.section.dto.SectionDto;
import com.rs.teach.mapper.studyAttr.entity.Practice;
import com.rs.teach.mapper.studyAttr.entity.Testpaper;
import com.rs.teach.mapper.studyAttr.vo.TrainCourseVo;
import com.rs.teach.service.section.SectionService;
import com.rs.teach.service.studyAttr.CourseService;
import com.rs.teach.service.studyAttr.TestAndWorkService;
import com.rs.teach.service.training.TrainCourseService;
import com.rs.teach.service.training.TrainSectionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-15 11:07
 */
@Controller
@RequestMapping("/section")
public class SectionController {

    private static final Logger logger = Logger.getLogger(SectionController.class);

    @Autowired
    private SectionService sectionService;

    @Autowired
    private TrainSectionService trainSectionService;

    @Autowired
    private TestAndWorkService testAndWorkService;

    @Autowired
    private TrainCourseService trainCourseService;

    @Autowired
    private CourseService courseService;

    /**
     * 添加章节
     *
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
        //课件文件上传
        Map<String, Object> courseWareMap = FileUpDownUtil.fileUpLoad(request, courseWareFile);
        //文件上传是否成功
        if (!(courseWareMap != null && "0".equals(courseWareMap.get("code")))) {
            bean.addError(courseWareMap.get("message").toString());
            return bean;
        }
        //sectionDto.setSectionId(courseWareMap.get("upLoadId").toString());

        sectionDto.setCoursewareId(courseWareMap.get("upLoadId").toString());
        sectionDto.setLitterSectionUrl(courseWareMap.get(("sectionUrl")).toString());
        sectionDto.setUpdateFileName(courseWareMap.get("updateFileName").toString());
        sectionDto.setLitterSectionType(courseWareMap.get("sectionType").toString());

        //作业文件上传
        Map<String, Object> practiceMap = FileUpDownUtil.fileUpLoad(request, practiceFile);
        //文件上传是否成功
        if (!(practiceMap != null && "0".equals(practiceMap.get("code")))) {
            bean.addError(practiceMap.get("message").toString());
            return bean;
        }
        Practice practice = new Practice();
        practice.setPracticeId(practiceMap.get("upLoadId").toString());
        practice.setPracticeFileName(practiceMap.get("updateFileName").toString());
        practice.setPracticeUrl(practiceMap.get("pdfUrl").toString());

        //试卷文件上传
        Map<String, Object> testpaperMap = FileUpDownUtil.fileUpLoad(request, testpaperFile);
        //文件上传是否成功
        if (!(testpaperMap != null && "0".equals(testpaperMap.get("code")))) {
            bean.addError(testpaperMap.get("message").toString());
            return bean;
        }
        Testpaper testpaper = new Testpaper();
        testpaper.setTestpaperId(testpaperMap.get("upLoadId").toString());
        testpaper.setTestpaperName(testpaperMap.get("updateFileName").toString());
        testpaper.setTestpaperUrl(testpaperMap.get("pdfUrl").toString());

        sectionDto.setPracticeId(practice.getPracticeId());
        sectionDto.setPracticeId(testpaper.getTestpaperId());

        try {
            testAndWorkService.insertPractice(practice);
            testAndWorkService.insertTestpaper(testpaper);
            if (StrUtil.equals("1", sectionDto.getIsTrain())) {
                trainSectionService.addTrainSection(sectionDto);
            } else {
                sectionService.addSection(sectionDto);
            }
            bean.addSuccess("添加成功！");
        } catch (Exception e) {
            logger.error("课程-添加-失败", e);
            bean.addError("添加失败");
        }
        return bean;
    }

    /**
     * 查询所有章节
     *
     * @param sectionDto(只需要isTrain)
     * @return
     */
    @RequestMapping(value = "/selectSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectSection(@RequestBody SectionDto sectionDto) {
        ResponseBean bean = new ResponseBean();
        //返回结果集
        List<TrainCourseVo> trainCourseVoList = new ArrayList<>();
        if (StrUtil.equals("1", sectionDto.getIsTrain())) {
            //获取所有课程信息
            List<TrainCourseVo> trainCourseVos = trainCourseService.selectTrainCourse();
            for (TrainCourseVo vo : trainCourseVos) {
                //单个课程以及含有的章节信息
                TrainCourseVo trainCourseVo = trainSectionService.selectCourseSection(vo.getTrainCourseId());
                trainCourseVoList.add(trainCourseVo);
            }
        } else {
            //获取所有课程信息id
            List<TrainCourseVo> trainCourseVos = courseService.selectTrainCourse();
            for (TrainCourseVo vo : trainCourseVos) {
                //单个课程以及含有的章节信息
                TrainCourseVo trainCourseVo = sectionService.selectCourseSection(vo.getTrainCourseId());
                trainCourseVoList.add(trainCourseVo);
            }
        }
        bean.addSuccess(trainCourseVoList);
        return bean;
    }

    /**
     * 修改所有章节
     * @param courseWareFile
     * @param practiceFile
     * @param testpaperFile
     * @param sectionDto
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateSection", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean updateSection(@RequestParam("courseWareFile") MultipartFile courseWareFile,
                                      @RequestParam("practiceFile") MultipartFile practiceFile,
                                      @RequestParam("testpaperFile") MultipartFile testpaperFile,
                                      SectionDto sectionDto,
                                      HttpServletRequest request) {
        ResponseBean bean = new ResponseBean();
        Practice practice = new Practice();
        Testpaper testpaper = new Testpaper();
        if (!courseWareFile.isEmpty()) {
            //课件文件上传
            Map<String, Object> courseWareMap = FileUpDownUtil.fileUpLoad(request, courseWareFile);
            //文件上传是否成功
            if (!(courseWareMap != null && "0".equals(courseWareMap.get("code")))) {
                bean.addError(courseWareMap.get("message").toString());
                return bean;
            }
            //sectionDto.setSectionId(courseWareMap.get("upLoadId").toString());

            sectionDto.setCoursewareId(courseWareMap.get("upLoadId").toString());
            sectionDto.setLitterSectionUrl(courseWareMap.get(("sectionUrl")).toString());
            sectionDto.setUpdateFileName(courseWareMap.get("updateFileName").toString());
            sectionDto.setLitterSectionType(courseWareMap.get("sectionType").toString());
        }
        if (!practiceFile.isEmpty()) {
            //作业文件上传
            Map<String, Object> practiceMap = FileUpDownUtil.fileUpLoad(request, practiceFile);
            //文件上传是否成功
            if (!(practiceMap != null && "0".equals(practiceMap.get("code")))) {
                bean.addError(practiceMap.get("message").toString());
                return bean;
            }
            practice.setPracticeId(practiceMap.get("upLoadId").toString());
            practice.setPracticeFileName(practiceMap.get("updateFileName").toString());
            practice.setPracticeUrl(practiceMap.get("pdfUrl").toString());

            sectionDto.setPracticeId(practice.getPracticeId());
        }
        if (!testpaperFile.isEmpty()) {
            //试卷文件上传
            Map<String, Object> testpaperMap = FileUpDownUtil.fileUpLoad(request, testpaperFile);
            //文件上传是否成功
            if (!(testpaperMap != null && "0".equals(testpaperMap.get("code")))) {
                bean.addError(testpaperMap.get("message").toString());
                return bean;
            }
            testpaper.setTestpaperId(testpaperMap.get("upLoadId").toString());
            testpaper.setTestpaperName(testpaperMap.get("updateFileName").toString());
            testpaper.setTestpaperUrl(testpaperMap.get("pdfUrl").toString());

            sectionDto.setPracticeId(testpaper.getTestpaperId());
        }
        try {
            if (ObjectUtil.isNotNull(practice)) {
                //获取之前文件路径

                testAndWorkService.updatePractice(practice);
                //删除之前保留的文件

            }
            if (ObjectUtil.isNotNull(testpaper)) {
                testAndWorkService.updateTestpaper(testpaper);
                //删除之前保留的文件

            }
            if (StrUtil.equals("1", sectionDto.getIsTrain())) {
                trainSectionService.updateTrainSection(sectionDto);
            } else {
                sectionService.updateSection(sectionDto);
            }
            bean.addSuccess("修改成功！");
        } catch (Exception e) {
            logger.error("课程-修改-失败", e);
            bean.addError("修改失败");
        }
        return bean;
    }
}
