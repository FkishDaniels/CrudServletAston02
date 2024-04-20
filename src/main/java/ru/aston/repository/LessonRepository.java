package ru.aston.repository;

import ru.aston.model.Lesson;
import ru.aston.model.Professor;
import ru.aston.model.Student;

import java.util.List;

public interface LessonRepository {
    Lesson findById(long id);
    List<Student>findStudents(long id);
    Professor getProfessor(long id);

    void save(Lesson lesson);
    void delete(Lesson lesson);
    void update(Lesson lesson);
}
