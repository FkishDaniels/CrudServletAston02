package ru.aston.database;

import java.io.IOException;
import java.util.Properties;

public class DBConfigProperties {
    private static Properties properties = new Properties();

    private DBConfigProperties(){}
    static {
        try {
            properties.load(ConfigProperties.class.getResourceAsStream("database.properties"));
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
