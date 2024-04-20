package ru.aston.dto.MAPPER;

import ru.aston.dto.ProfessorDTO;
import ru.aston.model.Professor;

import java.util.function.Function;

public class ProfessorDtoMapper implements Function<Professor, ProfessorDTO> {
    @Override
    public ProfessorDTO apply(Professor professor) {
        return new ProfessorDTO(
                professor.getId(),
                professor.getName(),
                professor.getAge()
        );
    }
}
