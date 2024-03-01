package org.ksolves;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        DatabaseHandler db = new DatabaseHandler();
        Connection connection = null;
        try{
            connection = DbConnector.connection();
            if (connection != null){
                System.out.println("connection established");
            }else {
                System.out.println("error in connection");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        Scanner s = new Scanner(System.in);
        System.out.println ("---------------------Welcome-------------------------");
        System.out.println("Select an option: 1.Register to database       2. Login");
        int e = s.nextInt();
        System.out.println();
        if(e==1){
                System.out.println("Please enter name:");
                String name = s.next();
                System.out.println("Please enter age more than 18:");
                Integer age = s.nextInt();
                System.out.println("Please enter username:");
                String uname = s.next();
                Boolean b = db.insert_User(connection,name,age,uname);
                if(uname != null && b==true ){db.generate_pass(connection,uname);}
        } else if (e==2) {
                db.login_auth(connection);

        }
        else {
            System.out.println("Make available choice please");
        }
    }
}