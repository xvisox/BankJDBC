package com.company.bank.actions.admin;

import com.company.bank.actions.Action;
import com.company.bank.database.MySQL;
import com.company.bank.utilities.PasswordUtility;
import com.company.bank.utilities.UserUtility;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class EmployeeAddAction implements Action {
    private List<User> usersList;
    private Scanner sc;

    public EmployeeAddAction(List<User> usersList, Scanner sc) {
        this.usersList = usersList;
        this.sc = sc;
    }

    private void addEmployee() {
        String login, password;
        login = settingLogin();
        password = settingPassword();
        User user = new User(login, password, Role.EMPLOYEE, true);
        usersList.add(user);
        Statement stmt = null;
        Connection connection = MySQL.connection();
        try {
            stmt = connection.createStatement();
            String sql;
            sql = String.format("INSERT INTO `users` VALUES ('%s','%s','%s','%s')", login, password, "EMPLOYEE", "true");
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

    private String settingLogin() {
        String login;
        System.out.println("Enter login:");
        login = sc.nextLine();
        while (PasswordUtility.isAlreadyTaken(login, usersList)) {
            System.out.println("Username is already taken, try again");
            login = sc.nextLine();
        }
        return login;
    }

    private String settingPassword() {
        String password, securityInfo;
        securityInfo = "Password isnt secure, try following above instructions";

        System.out.println("Enter password: ");
        password = sc.nextLine();
        while (!PasswordUtility.passCheck(password)) {
            System.out.println(securityInfo);
            password = sc.nextLine();
        }
        System.out.println("Correct password");
        return password;
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public String getActionName() {
        return "Add Employee";
    }

    @Override
    public void execute() {
        addEmployee();
    }

    @Override
    public String toString() {
        return "Add Employee";
    }
}
