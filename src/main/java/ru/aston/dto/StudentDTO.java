package ru.aston.dto;

import lombok.Data;
import ru.aston.model.Lesson;

import java.util.List;
@Data
public class StudentDTO {
    private long id;
    private String name;
    private int age;
    private int course;

    public StudentDTO(long id, String name, int age, int course, List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }
}
