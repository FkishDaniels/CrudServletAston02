package ru.aston.dto.MAPPER;

import ru.aston.dto.LessonDTO;
import ru.aston.model.Lesson;

import java.util.function.Function;

public class LessonDtoMapper implements Function<Lesson, LessonDTO> {
    @Override
    public LessonDTO apply(Lesson lesson) {
        return new LessonDTO(
            lesson.getId(),
            lesson.getName()
        );
    }
}
