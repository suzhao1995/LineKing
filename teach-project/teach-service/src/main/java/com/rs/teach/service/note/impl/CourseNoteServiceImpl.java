package com.rs.teach.service.note.impl;

import com.rs.teach.mapper.note.dao.CourseNoteMapper;
import com.rs.teach.mapper.note.entity.CourseNote;
import com.rs.teach.mapper.section.entity.TrainSection;
import com.rs.teach.service.note.CourseNoteService;
import com.rs.teach.service.training.TrainSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 汪航
 * @Description
 * @create 2019-08-06 12:25
 */
@Service
public class CourseNoteServiceImpl implements CourseNoteService {

    @Autowired
    private CourseNoteMapper noteMapper;

    @Autowired
    private TrainSectionService trainSectionService;

    @Override
    public String selectNote(CourseNote courseNote) {

        String note = noteMapper.selectNote(courseNote);
        return note;
    }

    @Override
    public void saveNote(CourseNote courseNote) {
        TrainSection trainSection = trainSectionService.selectTrainSection(courseNote.getSectionId());
        courseNote.setCourseId(trainSection.getTrainCourseId());
        noteMapper.saveNote(courseNote);
    }

    @Override
    public void addAllNote(CourseNote courseNote) {
        noteMapper.addAllNote(courseNote);
    }
}
