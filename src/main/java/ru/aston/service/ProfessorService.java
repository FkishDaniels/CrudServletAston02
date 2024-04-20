package ru.aston.service;

import ru.aston.DAO.ProfessorDAO;
import ru.aston.database.ConnectionManager;
import ru.aston.dto.MAPPER.ProfessorDtoMapper;
import ru.aston.dto.ProfessorDTO;
import ru.aston.model.Lesson;
import ru.aston.model.Professor;

import java.util.Collections;
import java.util.List;

public class ProfessorService {
    private final ProfessorDAO professorRepository;
    private final ProfessorDtoMapper professorDtoMapper;

    public ProfessorService(ConnectionManager connectionManager, ProfessorDtoMapper professorDtoMapper) {
        this.professorRepository = new ProfessorDAO(connectionManager);
        this.professorDtoMapper = professorDtoMapper;
    }

    public ProfessorDTO findById(long id){
        Professor professor = professorRepository.findById(id);
        if(professor != null){
            return professorDtoMapper.apply(professor);
        }
        return null;
    }
    public void save(ProfessorDTO professorDTO){
        Professor professor = convertToModel(professorDTO);
        professorRepository.save(professor);
    }
    public void delete(ProfessorDTO professorDTO){
        Professor professor = convertToModel(professorDTO);
        professorRepository.delete(professor);
    }
    public void update(ProfessorDTO professorDTO){
        Professor professor = convertToModel(professorDTO);
        professorRepository.update(professor);
    }

    public List<Lesson> getLessons(long professorId){
        Professor professor = professorRepository.findById(professorId);
        if(professor != null){
            return professorRepository.getLessons(professor);
        }
        return Collections.emptyList();
    }

    public void addLesson(long professorId,long lessonId){
        professorRepository.addLesson(professorId,lessonId);
    }
    public void removeLesson(long professorId,long lessonId){
        Professor professor = professorRepository.findById(professorId);
        if(professor != null){
            professorRepository.removeLesson(professorId,lessonId);
        }
    }

    private Professor convertToModel(ProfessorDTO professorDTO) {
        Professor professor = new Professor();
        professor.setId(professorDTO.getId());
        professor.setName(professorDTO.getName());
        professor.setAge(professorDTO.getAge());
        return professor;
    }
}
