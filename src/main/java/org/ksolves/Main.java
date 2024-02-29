package org.ksolves;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        DatabaseHandler db = new DatabaseHandler();
        Connection conn =db.connect_to_db("my_task","postgres","ksolves");
        db.login_auth(conn);
    }
}