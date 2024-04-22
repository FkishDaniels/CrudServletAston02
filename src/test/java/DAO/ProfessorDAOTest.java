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
import ru.aston.database.ConnectionManager;
import ru.aston.model.Lesson;
import ru.aston.model.Professor;
import ru.aston.model.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

/*
@author Marukha
created on 21.04.2024 inside
the package - DAO
Have a good coding time inside this  class
*/
@Testcontainers
@FixMethodOrder(NAME_ASCENDING)
public class ProfessorDAOTest {
    @Container
    public static PostgreSQLContainer myContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test")
            .withPassword("test")
            .withUsername("test")
            .withInitScript("script.sql");
    private static ConnectionManager connectionManager;
    private static ProfessorDAO professorRepository;
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
                return DriverManager.getConnection(jdbcUrl,username,password);
            }
        };
        professorRepository = new ProfessorDAO(connectionManager);
    }

    /**
     * Saving test
     */
    @Test
    public void A() {
        Professor professor = new Professor();
        professor.setName("John");
        professor.setAge(20);

        assertTrue(professorRepository.save(professor));
        Professor professorFalse = new Professor();
        professorFalse.setName(null);
        assertFalse(professorRepository.save(professorFalse));
        assertNotNull(professorRepository.findById(1));

    }

    /**
     * Update test
     */
    @Test
    public void B() {
        Professor professor = new Professor();
        professor.setName("John");
        professor.setAge(20);

        assertTrue(professorRepository.save(professor));

        professor.setName("John Doe");
        professor.setId(1);

        assertTrue(professorRepository.update(professor));

        Professor updatedProfessor = professorRepository.findById(professor.getId());
        assertNotNull(updatedProfessor);
        assertEquals("John Doe", updatedProfessor.getName());
    }

    /**
     * Delete test
     */
    @Test
    public void C() {
        Professor professor = new Professor();
        professor.setName("Bob");
        professor.setAge(30);

        professorRepository.save(professor);

        Professor profFromDb = professorRepository.findById(4);
        assertNotNull(professorRepository.findById(profFromDb.getId()));
        assertTrue(professorRepository.delete(4));

        assertNull(professorRepository.findById(profFromDb.getId()));
    }
    /**
     * Add lesson test
     */
    @Test
    public void E() {
        LessonDAO lessonDAO = new LessonDAO(connectionManager);

        Professor professor = new Professor();
        professor.setName("John");
        professor.setAge(40);
        professorRepository.save(professor);
        professor.setId(1);

        Lesson lesson = new Lesson();
        lesson.setName("Math");
        lesson.setId(1);
        lessonDAO.save(lesson);

        assertTrue(professorRepository.addLesson(professor.getId(), lesson.getId()));
        assertFalse(professorRepository.addLesson(professor.getId(), 228));
    }
    /**
     *Remove lesson test
     */
    @Test
    public void F() {
        LessonDAO lessonDAO = new LessonDAO(connectionManager);
        Lesson lesson = lessonDAO.findById(1);

        Professor professor = professorRepository.findById(1);

        assertTrue(professorRepository.removeLesson(professor.getId(), lesson.getId()));
        assertFalse(professorRepository.removeLesson(professor.getId(), lesson.getId()));
    }
    /**
     *
     * Lessons return
     */
    @Test
    public void G() {
        LessonDAO lessonDAO = new LessonDAO(connectionManager);
        Lesson lesson = lessonDAO.findById(1);

        Professor professor = professorRepository.findById(1);

        assertTrue(professorRepository.getLessons(professor).isEmpty());

        professorRepository.addLesson(professor.getId(), lesson.getId());

        assertFalse(professorRepository.getLessons(professor).isEmpty());
    }



}
