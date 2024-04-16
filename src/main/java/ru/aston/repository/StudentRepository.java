package ru.aston.repository;

import ru.aston.model.Student;

import java.util.List;

public interface StudentRepository {
    Student findById(long id);
    void save(Student student);
    void delete(Student student);
    void update(Student student);
}
