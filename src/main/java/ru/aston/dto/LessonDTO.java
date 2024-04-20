package ru.aston.dto;

import lombok.Data;
import ru.aston.model.Professor;
import ru.aston.model.Student;

import java.util.List;
@Data
public class LessonDTO {
    private long id;
    private String name;
    //Many-To-Many
    private List<Student> students;
    //Many-To-One
    private Professor professor;

    public LessonDTO(long id, String name, List<Student> students, Professor professor) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.professor = professor;
    }
}
