package com.company.bank.actions.registration;

import com.company.bank.database.MySQL;
import com.company.bank.utilities.UserUtility;
import com.company.bank.utilities.PasswordUtility;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class SignUp {
    private List<User> usersList;
    private Scanner sc;

    public SignUp(List<User> usersList, Scanner sc) {
        this.usersList = usersList;
        this.sc = sc;
    }

    private void signUp() {
        String login, password;
        login = settingLogin();
        password = settingPassword();
        User user = new User(login, password, Role.CUSTOMER, false);
        usersList.add(user);
        Statement stmt = null;
        Connection connection = MySQL.connection();
        try {
            stmt = connection.createStatement();
            String sql;
            sql = String.format("INSERT INTO `users` VALUES ('%s','%s','%s','%s')", login, password, "CUSTOMER", "false");
            stmt.executeUpdate(sql);
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    private String settingPassword() {
        String password, securityInfo;
        securityInfo = "Your password isnt secure, try following above instructions";

        System.out.println("Enter password: ");
        password = sc.nextLine();
        while (!PasswordUtility.passCheck(password)) {
            System.out.println(securityInfo);
            password = sc.nextLine();
        }
        System.out.println("Correct password");
        return password;
    }

    private String settingLogin() {
        String login;
        System.out.println("Enter login:");
        login = sc.nextLine();
        while (PasswordUtility.isAlreadyTaken(login, usersList) || login.length() > 12) {
            if (PasswordUtility.isAlreadyTaken(login, usersList)) {
                System.out.println("Username already taken.");
            }
            if (login.length() > 12) {
                System.out.println("Username max length is 12 characters.");
            }
            login = sc.nextLine();
        }
        return login;
    }

    public void execute() {
        signUp();
    }
}
