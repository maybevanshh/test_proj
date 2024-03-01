package org.ksolves;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbHandler {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseHandler.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find db.properties");
                System.exit(1);
            }
            properties.load(input);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static String getDbUrl() {

        return properties.getProperty("db.url");
    }

    public static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }
}

