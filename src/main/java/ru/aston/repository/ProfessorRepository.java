package ru.aston.repository;

import ru.aston.model.Lesson;
import ru.aston.model.Professor;

import java.util.List;

public interface ProfessorRepository {
    Professor findById(long id);
    List<Lesson> getLessons(long id);
    void save(Professor professor);
    void delete(Professor professor);
    void update(Professor professor);
}
