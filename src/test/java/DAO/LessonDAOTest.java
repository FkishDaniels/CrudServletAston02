package DAO;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.DAO.LessonDAO;
import ru.aston.DAO.ProfessorDAO;
import ru.aston.DAO.StudentDAO;
import ru.aston.database.ConnectionManager;
import ru.aston.model.Lesson;
import ru.aston.model.Professor;
import ru.aston.model.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

@Testcontainers
@FixMethodOrder(NAME_ASCENDING)
public class LessonDAOTest {

    @Container
    public static PostgreSQLContainer myContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test")
            .withPassword("test")
            .withUsername("test")
            .withInitScript("script.sql");

    private static ConnectionManager connectionManager;
    private static LessonDAO lessonDAO;

    @BeforeAll
    public static void setUp() throws Exception {
        myContainer.start();
        String jdbcUrl = myContainer.getJdbcUrl();
        String username = myContainer.getUsername();
        String password = myContainer.getPassword();

        connectionManager = new ConnectionManager() {
            @Override
            public Connection getConnection() throws SQLException {
                DriverManager.registerDriver(new Driver());
                return DriverManager.getConnection(jdbcUrl, username, password);
            }
        };

        lessonDAO = new LessonDAO(connectionManager);
    }

    /**
     * Find by id and Save test
     */
    @Test
    public void A() {
        Lesson lesson = new Lesson();
        lesson.setName("Test Lesson");
        lessonDAO.save(lesson);

        Lesson retrievedLesson = lessonDAO.findById(1);

        assertNotNull(retrievedLesson);
        assertEquals("Test Lesson", retrievedLesson.getName());
    }

    /**
     * Get Students test
     */
    @Test
    public void B() {

        Lesson lesson = lessonDAO.findById(1);


       Student student = new Student();
       student.setAge(23);
       student.setName("Vanya");
       student.setCourse(1);
       student.setId(1);
       assertTrue(lessonDAO.findStudents(lesson.getId()).isEmpty());
       StudentDAO studentDAO = new StudentDAO(connectionManager);
       studentDAO.save(student);
       studentDAO.addLesson(student.getId(),lesson.getId());
       assertFalse(lessonDAO.findStudents(lesson.getId()).isEmpty());
    }

    /**
     * Get professor test
     */
    @Test
    public void C(){
        Lesson lesson = lessonDAO.findById(1);

        Professor professor = new Professor();
        professor.setId(1);
        professor.setName("Andrew");
        professor.setAge(93);

        assertNull(lessonDAO.getProfessor(lesson.getId()));
        ProfessorDAO professorDAO = new ProfessorDAO(connectionManager);
        professorDAO.save(professor);
        professorDAO.addLesson(professor.getId(),lesson.getId());
        assertNotNull(lessonDAO.getProfessor(lesson.getId()));
    }
    /**
     * Update test
     */
    @Test
    public void D(){
        Lesson lesson = lessonDAO.findById(1);
        lesson.setId(1337);
        assertFalse(lessonDAO.update(lesson));
        lesson.setName("Test updated");
        lesson.setId(1);
        assertTrue(lessonDAO.update(lesson));
    }
    /**
     * Delete test
     */
    @Test
    public void E(){
        Lesson lesson = new Lesson();
        lesson.setId(2);
        lesson.setName("Test");
        lessonDAO.save(lesson);
        assertTrue(lessonDAO.delete(2));
    }
}
