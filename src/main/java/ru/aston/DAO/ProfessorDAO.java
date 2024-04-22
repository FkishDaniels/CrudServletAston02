package ru.aston.DAO;

import ru.aston.database.ConnectionManager;
import ru.aston.model.Lesson;
import ru.aston.model.Professor;
import ru.aston.repository.ProfessorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO implements ProfessorRepository {
    private final ConnectionManager connectionManager;

    public ProfessorDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Professor findById(long id) {
        String query = "SELECT id, name, age FROM professor WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
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
    public boolean save(Professor professor) {
        String query = "INSERT INTO professor (name, age) VALUES (?, ?)";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,professor.getName());
            preparedStatement.setInt(2,professor.getAge());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        String query = "DELETE FROM professor WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1,id);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Professor professor) {
        String query = "UPDATE professor SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setInt(2,professor.getAge());
            preparedStatement.setLong(3,professor.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addLesson(long professorId, long lessonId) {
        String query = "INSERT INTO lesson_professor (lesson_id, professor_id) VALUES (?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, lessonId);
            preparedStatement.setLong(2, professorId);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Lesson> getLessons(Professor professor) {
        List<Lesson> lessons = new ArrayList<>();
        String query = "SELECT l.id, l.name " +
                "FROM lesson l " +
                "JOIN lesson_professor ls ON l.id = ls.lesson_id " +
                "WHERE ls.professor_id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, professor.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(resultSet.getLong("id"));
                    lesson.setName(resultSet.getString("name"));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

    @Override
    public boolean removeLesson(long professorId, long oldLessonId) {
        String query = "DELETE FROM lesson_professor where professor_id = ? AND lesson_id = ?";
        try(Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,professorId);
            preparedStatement.setLong(2,oldLessonId);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
