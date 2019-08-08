package com.rs.teach.mapper.note.dao;

import com.rs.teach.mapper.note.entity.CourseNote;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:28
 */
public interface CourseNoteMapper {

    String selectNote(CourseNote courseNote);

    void saveNote(CourseNote courseNote);

    void addAllNote(CourseNote courseNote);
}
