package com.rs.teach.service.note;

import com.rs.teach.mapper.note.entity.CourseNote;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:24
 */
public interface CourseNoteService {

    /**
     * 查询笔记信息
     * @param courseNote
     * @return
     */
    String selectNote(CourseNote courseNote);

    void saveNote(CourseNote courseNote);

    void addAllNote(CourseNote courseNote);
}