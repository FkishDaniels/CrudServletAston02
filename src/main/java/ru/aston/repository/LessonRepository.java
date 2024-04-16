package ru.aston.repository;

import ru.aston.model.Lesson;
import ru.aston.model.Professor;
import ru.aston.model.Student;

import java.util.List;

public interface LessonRepository {
    Lesson findById(long id);
    List<Student>findStudents(long id);
    Professor getProfessor(long id);
}
