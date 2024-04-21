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
    boolean addLesson(long studentId, long lessonId);
    boolean removeLesson(Student student, Lesson oldLesson);
}
