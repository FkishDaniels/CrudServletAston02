package ru.aston.repository;

import ru.aston.model.Lesson;
import ru.aston.model.Student;

import java.util.List;

public interface StudentRepository {
    Student findById(long id);
    boolean save(Student student);
    boolean delete(Student student);
    boolean update(Student student);

    List<Lesson> getLessons(Student student);
    void addLesson(long studentId, long lessonId);
    void removeLesson(Student student, Lesson oldLesson);
}
