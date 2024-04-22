package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.DAO.StudentDAO;
import ru.aston.database.ConnectionManager;
import ru.aston.dto.MAPPER.StudentDtoMapper;
import ru.aston.dto.StudentDTO;
import ru.aston.model.Student;
import ru.aston.service.StudentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private StudentDAO studentDAO;

    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        studentService = new StudentService(connectionManager, new StudentDtoMapper());
    }

    @Test
    void findById_ValidId_ReturnsStudentDTO() throws SQLException {
        long id = 1;
        StudentDTO expectedStudentDTO = new StudentDTO();
        expectedStudentDTO.setId(id);
        expectedStudentDTO.setName("Test Student");
        expectedStudentDTO.setAge(0);
        expectedStudentDTO.setCourse(0);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("name")).thenReturn("Test Student");
        when(resultSet.getInt("age")).thenReturn(0);
        when(resultSet.getInt("course")).thenReturn(0);

        StudentDTO result = studentService.findById(id);

        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setLong(1, id);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getLong("id");
        verify(resultSet).getString("name");
        verify(resultSet).getInt("age");
        verify(resultSet).getInt("course");
        assertEquals(expectedStudentDTO, result);
    }

    @Test
    void save_ValidStudentDTO_CallsStudentRepositorySave() throws SQLException {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(1);
        studentDTO.setName("Test Student");
        studentDTO.setAge(20);
        studentDTO.setCourse(3);
        Student student = new Student();
        student.setId(1);
        student.setName("Test Student");
        student.setAge(20);
        student.setCourse(3);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        studentService.save(studentDTO);

        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "Test Student");
        verify(preparedStatement).setInt(2, 20);
        verify(preparedStatement).setInt(3, 3);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void delete_ValidId_CallsStudentRepositoryDelete() throws SQLException {
        long id = 1;
        Student student = new Student();
        student.setId(id);

        when(connectionManager.getConnection())
                .thenReturn(connection)
                .thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(resultSet.next()).thenReturn(true);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getLong("id")).thenReturn(id);




        studentService.delete(id);

        verify(connectionManager,times(2)).getConnection();
        verify(preparedStatement,times(2)).setLong(1, id);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void update_ValidId_CallsStudentRepositoryUpdate() throws SQLException {
        long id = 1;
        Student student = new Student();
        student.setId(id);

        when(connectionManager.getConnection())
                .thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        studentService.update(new StudentDTO(1,"1",1,1));

        verify(connectionManager).getConnection();
        verify(preparedStatement).setLong(4,1);
        verify(preparedStatement).setString(1,"1");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void addLeson() throws SQLException{
        long studentId = 1;
        long lessonId = 1;

        StudentDTO student = new StudentDTO();
        student.setId(studentId);

        when(connectionManager.getConnection())
                .thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);


        studentService.addLesson(studentId, lessonId);

        verify(connectionManager).getConnection();
        verify(preparedStatement).setLong(1,lessonId);
        verify(preparedStatement).setLong(2,studentId);
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void getLessons() throws SQLException {
        long studentId = 1;
        StudentDTO student = new StudentDTO();
        student.setId(studentId);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        studentService.getLessons(studentId);

        verify(connectionManager,times(2)).getConnection();
        verify(preparedStatement).setLong(1,studentId);
        verify(preparedStatement,times(2)).executeQuery();
    }

    @Test
    void removeLessons() throws SQLException{
        long studentId = 1;
        long lessonId = 1;
        StudentDTO student = new StudentDTO();
        student.setId(studentId);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);


        studentService.removeLesson(studentId,lessonId);

        verify(connectionManager,times(2)).getConnection();
        verify(preparedStatement).setLong(1,lessonId);
        verify(preparedStatement).setLong(2,studentId);
        verify(preparedStatement).executeUpdate();
    }

}
