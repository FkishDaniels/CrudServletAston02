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

    public ProfessorDTO(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;

    }

    public ProfessorDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ProfessorDTO(){}
}
