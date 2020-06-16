package com.company.bank.database;

import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.sql.*;

public class MySQL {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/bank";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static Connection connection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return conn;
    }

//    Statement stmt = null;
//    Connection connection = MySQL.connection();
//        try {
//        stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
//
//        rs.close();
//        stmt.close();
//        connection.close();
//    } catch (SQLException se) {
//        //Handle errors for JDBC
//        se.printStackTrace();
//    } catch (Exception e) {
//        //Handle errors for Class.forName
//        e.printStackTrace();
//    }
}
