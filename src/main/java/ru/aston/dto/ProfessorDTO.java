package ru.aston.dto;

import lombok.Data;
import ru.aston.model.Lesson;

import java.util.List;
@Data
public class ProfessorDTO {
    private long id;
    private String name;
    private int age;
    //One-To-Many
    private List<Lesson> lessons;

    public ProfessorDTO(long id, String name, int age, List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.lessons = lessons;
    }
}
