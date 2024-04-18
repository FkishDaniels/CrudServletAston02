package ru.aston.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConfigProperties {
    private static Properties properties = new Properties();

    private DBConfigProperties(){}
    static {
        try (InputStream inputStream = DBConfigProperties.class.getResourceAsStream("database.properties")){
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUsername(){
        return properties.getProperty("database.username");
    }

    public static String getPassword(){
        return properties.getProperty("database.password");
    }

    public static String getUrl(){
       return properties.getProperty("database.url");
    }

}
