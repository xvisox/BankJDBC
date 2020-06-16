package com.company.bank.utilities;

import com.company.bank.database.MySQL;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserUtility {

    public static List<User> loadUsers() {

        List<User> usersList = new ArrayList<>();
        Statement stmt = null;
        Connection connection = MySQL.connection();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                //Retrieve by column name
                String login = rs.getString("login");
                String password = rs.getString("password");
                String role = rs.getString("role");
                String isAccepted = rs.getString("isAccepted");
                Role parsedRole = Role.valueOf(role);
                boolean parsedIsAcc = Boolean.parseBoolean(isAccepted);
                User user = new User(login, password, parsedRole, parsedIsAcc);
                usersList.add(user);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        return usersList;
    }

    public static User findUser(List<User> usersList, String login) {
        for (User user : usersList) {
            if (login.equals(user.getLogin())) {
                return user;
            }
        }
        return null;
    }
}
