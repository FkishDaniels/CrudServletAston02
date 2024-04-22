package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.DAO.ProfessorDAO;
import ru.aston.DAO.StudentDAO;
import ru.aston.database.ConnectionManager;
import ru.aston.dto.MAPPER.ProfessorDtoMapper;
import ru.aston.dto.MAPPER.StudentDtoMapper;
import ru.aston.dto.ProfessorDTO;
import ru.aston.dto.StudentDTO;
import ru.aston.model.Professor;
import ru.aston.model.Student;
import ru.aston.service.ProfessorService;
import ru.aston.service.StudentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProfessorServiceTest {
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private ProfessorDAO professorDAO;

    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private ProfessorService professorService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        professorService = new ProfessorService(connectionManager, new ProfessorDtoMapper());
    }

    @Test
    void findById_ValidId_ReturnsProfessorDTO() throws SQLException {
        long id = 1;
        ProfessorDTO expectedProfessorDTO = new ProfessorDTO();
        expectedProfessorDTO.setId(id);
        expectedProfessorDTO.setName("Test Professor");
        expectedProfessorDTO.setAge(0);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("name")).thenReturn("Test Professor");
        when(resultSet.getInt("age")).thenReturn(0);


        ProfessorDTO result = professorService.findById(id);

        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setLong(1, id);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getLong("id");
        verify(resultSet).getString("name");
        verify(resultSet).getInt("age");
        assertEquals(expectedProfessorDTO, result);
    }
    @Test
    void save_ValidProfessorDTO_CallsProfessorRepositorySave() throws SQLException {
        ProfessorDTO rofessorDTO = new ProfessorDTO();
        rofessorDTO.setId(1);
        rofessorDTO.setName("Test Professor");
        rofessorDTO.setAge(20);

        Professor student = new Professor();
        student.setId(1);
        student.setName("Test Professor");
        student.setAge(20);


        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        professorService.save(rofessorDTO);

        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "Test Professor");
        verify(preparedStatement).setInt(2, 20);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void delete_ValidId_CallsStudentRepositoryDelete() throws SQLException {
        long id = 1;
        Professor professor = new Professor();
        professor.setId(id);

        when(connectionManager.getConnection())
                .thenReturn(connection)
                .thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);


        professorService.delete(id);

        verify(connectionManager).getConnection();
        verify(preparedStatement).setLong(1, id);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void update_ValidId_CallsStudentRepositoryUpdate() throws SQLException {
        long id = 1;
        Professor professor = new Professor();
        professor.setId(id);

        when(connectionManager.getConnection())
                .thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        professorService.update(new ProfessorDTO(1,"1",1));

        verify(connectionManager).getConnection();
        verify(preparedStatement).setLong(3,1);
        verify(preparedStatement).setString(1,"1");
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void addLeson() throws SQLException{
        long proffId = 1;
        long lessonId = 1;

        ProfessorDTO professor = new ProfessorDTO();
        professor.setId(proffId);

        when(connectionManager.getConnection())
                .thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);


        professorService.addLesson(proffId, lessonId);

        verify(connectionManager).getConnection();
        verify(preparedStatement).setLong(1,lessonId);
        verify(preparedStatement).setLong(2,proffId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void getLessons() throws SQLException {
        long professorId = 1;
        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(professorId);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        professorService.getLessons(professorId);

        verify(connectionManager,times(2)).getConnection();
        verify(preparedStatement).setLong(1,professorId);
        verify(preparedStatement,times(2)).executeQuery();
    }

    @Test
    void removeLessons() throws SQLException{
        long professorId = 1;
        long lessonId = 1;
        ProfessorDTO professor = new ProfessorDTO();
        professor.setId(professorId);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);


        professorService.removeLesson(professorId,lessonId);

        verify(connectionManager,times(2)).getConnection();
        verify(preparedStatement).setLong(2,professorId);
        verify(preparedStatement).executeUpdate();
    }
}
