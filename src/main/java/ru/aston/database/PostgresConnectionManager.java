package ru.aston.database;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionManager implements ConnectionManager{
    @Override
    public Connection getConnection() throws SQLException {
        try {
            String url = DBConfigProperties.getUrl();
            String password = DBConfigProperties.getPassword();
            String username = DBConfigProperties.getUsername();
            System.out.println(url);
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(url,username,password);
        }catch (ExceptionInInitializerError e){

        }
        return null;
    }
}
