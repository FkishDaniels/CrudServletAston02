package ModelAndDTO;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.aston.DAO.StudentDAO;
import ru.aston.database.ConnectionManager;
import ru.aston.model.Student;
import ru.aston.repository.StudentRepository;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

@Testcontainers
@FixMethodOrder(NAME_ASCENDING)
public class StudentDAOTest {

    @Container
    public static PostgreSQLContainer myContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test")
            .withPassword("test")
            .withUsername("test")
            .withInitScript("script.sql");
    private static ConnectionManager connectionManager;
    private static StudentDAO studentRepository;

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
        studentRepository = new StudentDAO(connectionManager);
    }
    @AfterAll
    public static void tearDown() throws Exception {

    }

    @Test
    public void testA() throws Exception {

        try (Connection connection = connectionManager.getConnection()
                ;Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute("SELECT 1 FROM lesson"));
            assertTrue(stmt.execute("SELECT 1 FROM professor"));
            assertTrue(stmt.execute("SELECT 1 FROM student"));
            assertTrue(stmt.execute("SELECT 1 FROM lesson_student"));
            assertTrue(stmt.execute("SELECT 1 FROM lesson_professor"));
        }
    }
    @Test
    public void testB() {
        Student student = new Student();
        student.setName("John");
        student.setAge(20);
        student.setCourse(1);

        assertTrue(studentRepository.save(student));
        students();
        assertNotNull(studentRepository.findById(1));

    }
    @Test
    public void testC() {
        Student student = new Student();
        student.setName("Alice");
        student.setAge(25);
        student.setCourse(3);

        studentRepository.save(student);
        students();
        student.setName("Alice Smith");
        student.setId(2);
        assertTrue(studentRepository.update(student));

        Student updatedStudent = studentRepository.findById(2);
        assertNotNull(updatedStudent);
        assertEquals("Alice Smith", updatedStudent.getName());

    }

    @Test
    public void testD() {
        Student student = new Student();
        student.setName("Bob");
        student.setAge(30);
        student.setCourse(2);

        studentRepository.save(student);
        names();
        students();
        long id = takeSequence();
        System.out.println(id);
        Student studFromDb = studentRepository.findById(3);
        assertNotNull(studentRepository.findById(studFromDb.getId()));
        assertTrue(studentRepository.delete(studFromDb));

        assertNull(studentRepository.findById(studFromDb.getId()));
    }



    private static long takeSequence(){
        try (Connection connection = DriverManager.getConnection(myContainer.getJdbcUrl(), myContainer.getUsername(), myContainer.getPassword());
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT last_value FROM student_id_seq;");
            if (resultSet.next()) {
                long currentValue = resultSet.getLong(1);
                System.out.println("Текущее значение последовательности: " + currentValue);
                return currentValue;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private void names(){
        try (Connection connection = DriverManager.getConnection(myContainer.getJdbcUrl(), myContainer.getUsername(), myContainer.getPassword());
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema = 'public'");
            while (resultSet.next()) {
                String sequenceName = resultSet.getString("sequence_name");
                System.out.println("Найдена последовательность: " + sequenceName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void students(){
        try (Connection connection = DriverManager.getConnection(myContainer.getJdbcUrl(), myContainer.getUsername(), myContainer.getPassword());
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student;");
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setCourse(resultSet.getInt("course"));
                System.out.println(student.getId()+" "+student.getName()+" ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
