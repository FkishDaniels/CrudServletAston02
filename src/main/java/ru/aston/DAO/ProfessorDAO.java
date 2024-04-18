package ru.aston.DAO;

import ru.aston.database.ConnectionManager;
import ru.aston.model.Lesson;
import ru.aston.model.Professor;
import ru.aston.model.Student;
import ru.aston.repository.ProfessorRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void save(Professor professor) {
        String query = "INSERT INTO professor (name, age) VALUES (?, ?)";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1,professor.getName());
            preparedStatement.setInt(2,professor.getAge());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Professor professor) {
        String query = "DELETE FROM professor WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1,professor.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Professor professor) {
        String query = "UPDATE professor SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setInt(2,professor.getAge());
            preparedStatement.setLong(3,professor.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addLesson(Professor professor, Lesson lesson) {

    }

    @Override
    public List<Lesson> getLessons(Professor professor) {
        return null;
    }

    @Override
    public void removeLesson(Professor professor, Lesson oldLesson) {

    }
}
