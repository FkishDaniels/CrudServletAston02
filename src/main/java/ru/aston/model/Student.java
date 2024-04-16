package ru.aston.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private int course;
    //Many-to-Many
    private List<Lesson> lessons;

}
