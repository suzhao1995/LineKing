package com.rs.teach.mapper.note.dao;

import com.rs.teach.mapper.note.entity.CourseNote;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:28
 */
public interface CourseNoteMapper {

    /**
     * @param courseNote
     * 判断此记录是否存在
     * */
    int isEmpty(CourseNote courseNote);

    /**
     * 修改笔记
     * @param courseNote
     */
    void updateNote(CourseNote courseNote);

    /**
     * 添加笔记
     * @param courseNote
     */
    void addNote(CourseNote courseNote);

    /**
     * 查询笔记
     * @param courseNote
     * @return
     */
    String selectNote(CourseNote courseNote);

    /**
     * 批量插入笔记表
     * @param courseNote
     */
    void addAllNote(CourseNote courseNote);


}
