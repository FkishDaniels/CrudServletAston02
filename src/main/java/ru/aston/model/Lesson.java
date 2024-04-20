package ru.aston.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Lesson {
    private long id;
    private String name;
    //Many-To-Many
    private List<Student> students;
    //Many-To-One
    private Professor professor;
}
