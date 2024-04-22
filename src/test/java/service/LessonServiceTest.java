package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.database.ConnectionManager;
import ru.aston.dto.LessonDTO;
import ru.aston.dto.MAPPER.LessonDtoMapper;
import ru.aston.model.Lesson;
import ru.aston.repository.LessonRepository;
import ru.aston.service.LessonService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private LessonService lessonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        lessonService = new LessonService(connectionManager, new LessonDtoMapper());
    }

    @Test
    void findById_ValidId_ReturnsLessonDTO() throws SQLException {
        long id = 1;
        LessonDTO expectedLessonDTO = new LessonDTO();
        expectedLessonDTO.setId(id);
        expectedLessonDTO.setName("Test Lesson");

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("name")).thenReturn("Test Lesson");

        LessonDTO result = lessonService.findById(id);

        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setLong(1, id);
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getLong("id");
        verify(resultSet).getString("name");
        assertEquals(expectedLessonDTO, result);
    }

    @Test
    void save_ValidLessonDTO_CallsLessonRepositorySave() throws SQLException {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(1);
        lessonDTO.setName("Test Lesson");
        Lesson lesson = new Lesson();
        lesson.setId(1);
        lesson.setName("Test Lesson");

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        lessonService.save(lessonDTO);


        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "Test Lesson");
        verify(preparedStatement).executeUpdate(); // changed to executeUpdate() instead of execute()
    }
    @Test
    void delete_ValidId_CallsLessonRepositoryDelete() throws SQLException {
        long id = 1;

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        lessonService.delete(id);

        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setLong(1, id);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void update_ValidLessonDTO_CallsLessonRepositoryUpdate() throws SQLException {
        // Arrange
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(1);
        lessonDTO.setName("Updated Lesson");
        Lesson lesson = new Lesson();
        lesson.setId(1);
        lesson.setName("Updated Lesson");

        when(connectionManager.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);


        lessonService.update(lessonDTO);

        verify(connectionManager).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "Updated Lesson");
        verify(preparedStatement).setLong(2, 1);
        verify(preparedStatement).executeUpdate();
    }

}