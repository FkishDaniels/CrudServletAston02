package ru.aston.service;

import ru.aston.DAO.LessonDAO;
import ru.aston.dto.LessonDTO;
import ru.aston.dto.MAPPER.LessonDtoMapper;
import ru.aston.dto.ProfessorDTO;
import ru.aston.model.Lesson;
import ru.aston.model.Professor;

public class LessonService {

    private final LessonDAO lessonRepository;
    private final LessonDtoMapper lessonDtoMapper;

    public LessonService(LessonDAO lessonRepository, LessonDtoMapper lessonDtoMapper) {
        this.lessonRepository = lessonRepository;
        this.lessonDtoMapper = lessonDtoMapper;
    }

    public LessonDTO findById(long id){
        Lesson lesson = lessonRepository.findById(id);
        if(lesson != null) return lessonDtoMapper.apply(lesson);
        return null;
    }

    public void save(LessonDTO lessonDTO){
        Lesson lesson = convertToModel(lessonDTO);
        lessonRepository.save(lesson);
    }

    public void delete(LessonDTO lessonDTO){
        Lesson lesson = convertToModel(lessonDTO);
        lessonRepository.delete(lesson);
    }

    public void update(LessonDTO lessonDTO){
        Lesson lesson = convertToModel(lessonDTO);
        lessonRepository.update(lesson);
    }
    private Lesson convertToModel(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setId(lessonDTO.getId());
        lesson.setName(lessonDTO.getName());
        return lesson;
    }
}
