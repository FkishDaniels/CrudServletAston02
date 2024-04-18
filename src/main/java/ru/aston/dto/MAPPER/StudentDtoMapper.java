package ru.aston.dto.MAPPER;

import ru.aston.dto.StudentDTO;
import ru.aston.model.Student;

import java.util.function.Function;

public class StudentDtoMapper implements Function<Student, StudentDTO> {
    @Override
    public StudentDTO apply(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getAge()
                ,student.getCourse(),
                student.getLessons()
        );
    }
}
