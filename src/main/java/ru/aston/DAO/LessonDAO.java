package ru.aston.DAO;

import ru.aston.database.ConnectionManager;
import ru.aston.model.Lesson;
import ru.aston.model.Professor;
import ru.aston.model.Student;
import ru.aston.repository.LessonRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO implements LessonRepository {
    private final ConnectionManager connectionManager;

    public LessonDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Lesson findById(long id) {
        String query = "SELECT id, name  FROM lesson WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(resultSet.getLong("id"));
                    lesson.setName(resultSet.getString("name"));
                    return lesson;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findStudents(long id) {
        // Метод findStudents находит студентов, которые учатся на данном уроке по его идентификатору (id).
        String query = "SELECT s.id, s.name, s.age, s.course " +
                "FROM student s " +
                "JOIN lesson_student ls ON s.id = ls.student_id " +
                "WHERE ls.lesson_id = ?";
        List<Student> students = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getLong("id"));
                    student.setName(resultSet.getString("name"));
                    student.setAge(resultSet.getInt("age"));
                    student.setCourse(resultSet.getInt("course"));
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public Professor getProfessor(long id) {
        String query = "SELECT p.id, p.name, p.age " +
                "FROM professor p " +
                "JOIN lesson_professor lp ON p.id = lp.professor_id " +
                "WHERE lp.lesson_id = ?";

        try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1,id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    Professor professor = new Professor();
                    professor.setId(resultSet.getLong("id"));
                    professor.setName(resultSet.getString("name"));
                    professor.setAge(resultSet.getInt("age"));
                    return professor;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Lesson lesson) {
        String query = "INSERT INTO lesson (name) VALUES (?)";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,lesson.getName());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Lesson lesson) {
        String query = "DELETE FROM lesson WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1,lesson.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Lesson lesson) {
        String query = "UPDATE lesson SET name = ? where id = ?";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, lesson.getName());
            preparedStatement.setLong(2,lesson.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
