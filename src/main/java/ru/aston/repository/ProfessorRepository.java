package ru.aston.repository;

import ru.aston.model.Lesson;
import ru.aston.model.Professor;

import java.util.List;

public interface ProfessorRepository {
    Professor findById(long id);
    void save(Professor professor);
    void delete(Professor professor);
    void update(Professor professor);
    void addLesson(Professor professor,Lesson lesson);
    List<Lesson> getLessons(Professor professor);
    void removeLesson(Professor professor, Lesson oldLesson);
}
