package ru.aston.database;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionManager implements ConnectionManager{
    @Override
    public Connection getConnection() throws SQLException {
        String url = DBConfigProperties.getUrl();
        String password = DBConfigProperties.getPassword();
        String username = DBConfigProperties.getUsername();

        DriverManager.registerDriver(new Driver());
        return DriverManager.getConnection(url,username,password);
    }
}
