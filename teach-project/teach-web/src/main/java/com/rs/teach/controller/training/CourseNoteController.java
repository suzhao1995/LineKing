package com.rs.teach.controller.training;

import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.note.entity.CourseNote;
import com.rs.teach.service.note.CourseNoteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 汪航
 * @Description 课程笔记Controller
 * @create 2019-08-07 12:08
 */
@Controller
@RequestMapping("/note")
public class CourseNoteController {

    private final static Logger logger = Logger.getLogger(SummaryController.class);

    @Autowired
    private CourseNoteService courseNoteService;

    /**
     * 保存笔记
     * param :
     * sessionKey //用户id
     * sectionId  //章节id
     * note      //笔记
     */
    @RequestMapping(value = "saveNote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean saveNode(@RequestBody CourseNote courseNote, String sessionKey) {
        ResponseBean responseBean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
        courseNote.setUserId(userId);
        try {
            courseNoteService.saveNote(courseNote);
            responseBean.addSuccess();
        } catch (Exception e) {
            logger.error("课后总结：保存失败", e);
            responseBean.addError("保存失败，系统异常");
        }
        return responseBean;
    }

    /**
     * 笔记数据回显
     * param
     * sessionKey; //用户id
     * sectionId;  //章节ID
     * courseId      //课程id
     */
    @RequestMapping(value = "selectNote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean selectNote(String sessionKey,@RequestBody CourseNote courseNote) {
        ResponseBean responseBean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
        courseNote.setUserId(userId);

        String note = courseNoteService.selectNote(courseNote);

        responseBean.addSuccess(note);
        return responseBean;
    }
}
