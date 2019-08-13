package com.rs.teach.mapper.note.dao;

import com.rs.teach.mapper.note.entity.CourseNote;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:28
 */
public interface CourseNoteMapper {

    int isEmpty(CourseNote courseNote);

    void updateNote(CourseNote courseNote);

    void addNote(CourseNote courseNote);

    String selectNote(CourseNote courseNote);

    void addAllNote(CourseNote courseNote);


}
