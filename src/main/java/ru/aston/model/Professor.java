package ru.aston.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Professor {
    private long id;
    private String name;
    private int age;
    //One-To-Many
    private List<Lesson> lessons;
}
