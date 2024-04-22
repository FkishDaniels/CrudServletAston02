package ru.aston.repository;

import ru.aston.model.Lesson;
import ru.aston.model.Professor;

import java.util.List;

public interface ProfessorRepository {
    Professor findById(long id);
    boolean save(Professor professor);
    boolean delete(long id);
    boolean update(Professor professor);
    boolean addLesson(long professorId, long lessonId);
    List<Lesson> getLessons(Professor professor);
    boolean removeLesson(long professorId, long oldLessonId);
}
