package org.ksolves;
import org.apache.commons.lang.RandomStringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Properties;

public class DatabaseHandler {

    private String ids(){
    return RandomStringUtils.randomAlphanumeric(10);
    }
    public Boolean insert_User(Connection conn,String name,Integer age,String username){
        try {
            Statement s1 = conn.createStatement();
            String checker = ("Select username from test1 where username='"+ username+"' ");
            ResultSet rs = s1.executeQuery(checker);
            if(!rs.next()){
                String id = ids();
                PreparedStatement st = conn.prepareStatement("INSERT INTO TEST1(ID, NAME, AGE, USERNAME) VALUES(?, ?, ?, ?) ");
//            String query=("insert into test1 values('"+ id +"' '"+ name +"' "+ age +" '"+ username +"');");
                st.setString(1, id);
                st.setString(2, name);
                st.setInt(3, age);
                st.setString(4, username);
                st.executeUpdate();
                st.close();
                System.out.println("User added");
                return true;
            }else {
            String tocheck = rs.getString("username");
            if(tocheck.equals(username)){
                System.out.println("User already exists, please try again");
                return false;
            }
            }
        }catch (Exception e){
            System.out.println(e);
        }return  false;
    }

    private String checkuser(Connection conn, String username){
        String s = new String();

        try{
            Statement statement ;
            String st = String.format("SELECT Id FROM test1 WHERE username='"+username+"';");
            statement= conn.createStatement();
            ResultSet rs = statement.executeQuery(st);
            rs.next();
            s= rs.getString(1);
        }catch (Exception e){
            System.out.println(e + "user does not exits probably");
        }
        return s;
    }

    private String pencrypt(String pass){
        try{
            StringBuilder sb = new StringBuilder();
            MessageDigest msgdigest = MessageDigest.getInstance("MD5");
            msgdigest.update(pass.getBytes());
            byte [] bytes= msgdigest.digest();
            for(byte b : bytes){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        }catch (Exception e){
            System.out.println(e + "hashing didnt work");
        }
        return "";
    }
    public void generate_pass(Connection conn,String username){

        try{
            String id = checkuser(conn,username);
            System.out.println("Please input your password :");
            BufferedReader inputpas = new BufferedReader(new InputStreamReader(System.in));

            //generating hashed password
            String passwordString = inputpas.readLine();
            String hashedpass = pencrypt(passwordString);
            System.out.println("-----------------Your Password is encrypted------------------");
            PreparedStatement st = conn.prepareStatement("INSERT INTO CRIDENTIALS(ID,USERNAME,PASSWORDHASH) VALUES(?, ?, ?)");
            st.setString(1,id);
            st.setString(2,username);
            st.setString(3,hashedpass);
            st.executeUpdate();
            st.close();
            System.out.println("User created successfully");
            }catch (Exception e){
            System.out.println(e);
        }
        }

        public void login_auth(Connection conn)  {

        BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
        System.out.print("input username please:");
            String username = null;
            String pass = null;
            try {
                username = reader.readLine();
                System.out.println("please input password:");
                pass = reader.readLine();
            } catch (IOException e) {
            }

            String query = ("SELECT USERNAME,PASSWORDHASH FROM CRIDENTIALS WHERE USERNAME='"+username+"'");

        Statement statement;
            try {
                statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(query);
                if(!rs.next()){
                    System.out.println("No user exists");
                }
                String hash = rs.getString("passwordhash");
                if (pencrypt(pass).equals(hash)){
                    System.out.println("User authourised");
                    System.out.println("Welcome , "+ username);
                }
                else {
                    System.out.println("Input correct pass please");
                }

            } catch (SQLException e) {
                System.out.println("perhaps try creating a user first");
            }


        }
}
