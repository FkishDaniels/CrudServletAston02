package ru.aston.service;


import ru.aston.DAO.StudentDAO;
import ru.aston.database.ConnectionManager;
import ru.aston.dto.MAPPER.StudentDtoMapper;
import ru.aston.dto.StudentDTO;
import ru.aston.model.Lesson;
import ru.aston.model.Student;

import java.util.Collections;
import java.util.List;

public class StudentService {
    private final StudentDAO studentRepository;
    private final StudentDtoMapper studentDtoMapper;

    public StudentService(ConnectionManager connectionManager, StudentDtoMapper studentDtoMapper) {
        this.studentRepository = new StudentDAO(connectionManager);
        this.studentDtoMapper = studentDtoMapper;
    }

    public StudentDTO findById(long id) {
        Student student = studentRepository.findById(id);
        if (student != null) {
            return studentDtoMapper.apply(student);
        }
        return null;
    }

    public void save(StudentDTO studentDTO) {
        Student student = convertToModel(studentDTO);
        studentRepository.save(student);
    }

    public void delete(long id) {
        Student student = studentRepository.findById(id);
        if (student != null) {
            studentRepository.delete(student);
        }
    }

    public void update(StudentDTO studentDTO) {
        Student student = convertToModel(studentDTO);
        studentRepository.update(student);
    }

    public List<Lesson> getLessons(long studentId) {
        Student student = studentRepository.findById(studentId);
        if (student != null) {
            return studentRepository.getLessons(student);
        }
        return Collections.emptyList();
    }

    public void addLesson(long studentId, long lessonId) {
        studentRepository.addLesson(studentId, lessonId);
    }

    public void removeLesson(long studentId, long lessonId) {
        Student student = studentRepository.findById(studentId);
        Lesson lesson = new Lesson();
        lesson.setId(lessonId);
        if (student != null) {
            studentRepository.removeLesson(student, lesson);
        }
    }


    private Student convertToModel(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setName(studentDTO.getName());
        student.setAge(studentDTO.getAge());
        student.setCourse(studentDTO.getCourse());
        return student;
    }
}

