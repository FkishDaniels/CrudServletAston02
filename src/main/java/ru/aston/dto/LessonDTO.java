package ru.aston.dto;

import lombok.Data;
import ru.aston.model.Professor;
import ru.aston.model.Student;

import java.util.List;
@Data
public class LessonDTO {
    private long id;
    private String name;


    public LessonDTO(long id, String name) {
        this.id = id;
        this.name = name;

    }
    public LessonDTO(){}
}
