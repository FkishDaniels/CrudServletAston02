package ru.aston.service;

import ru.aston.DAO.LessonDAO;
import ru.aston.database.ConnectionManager;
import ru.aston.dto.LessonDTO;
import ru.aston.dto.MAPPER.LessonDtoMapper;
import ru.aston.model.Lesson;


public class LessonService {

    private final LessonDAO lessonRepository;
    private final LessonDtoMapper lessonDtoMapper;

    public LessonService(ConnectionManager connectionManager, LessonDtoMapper lessonDtoMapper) {
        this.lessonRepository = new LessonDAO(connectionManager);
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

    public void delete(long id){
        lessonRepository.delete(id);
    }

    public void update(LessonDTO lessonDTO){
        Lesson lesson = convertToModel(lessonDTO);
        lessonRepository.update(lesson);
    }
    public Lesson convertToModel(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        lesson.setId(lessonDTO.getId());
        lesson.setName(lessonDTO.getName());
        return lesson;
    }
}
