package com.rs.teach.controller.training;

import com.rs.common.utils.ResponseBean;
import com.rs.common.utils.UserInfoUtil;
import com.rs.teach.mapper.note.entity.CourseNote;
import com.rs.teach.service.note.CourseNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @Autowired
    private CourseNoteService courseNoteService;

    /**
     * 保存笔记
     * param :
     *          userId //用户id
     *          sectionId  //章节id
     *          note      //笔记
     */
    @RequestMapping(value = "saveNote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean saveNode(CourseNote courseNote, String sessionKey) {
        ResponseBean responseBean = new ResponseBean();
        String userId = UserInfoUtil.getUserInfo(sessionKey).get("userId").toString();
        courseNote.setUserId(userId);
        courseNoteService.saveNote(courseNote);
        return responseBean;
    }
}
