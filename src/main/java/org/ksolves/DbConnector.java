package org.ksolves;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    public static Connection connection(){
        var jdbcurl = DbHandler.getDbUrl();
        var username = DbHandler.getDbUsername();
        var password = DbHandler.getDbPassword();
        try {
            return DriverManager.getConnection(jdbcurl,username,password);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return connection();
    }
}
