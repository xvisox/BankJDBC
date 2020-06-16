package com.company.bank.actions.admin;

import com.company.bank.database.MySQL;
import com.company.bank.utilities.UserUtility;
import com.company.bank.actions.Action;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class EmployeeRemove implements Action {
    private List<User> usersList;
    private Scanner sc;

    public EmployeeRemove(List<User> usersList, Scanner sc) {
        this.usersList = usersList;
        this.sc = sc;
    }

    private void employeeRemove() {
        String login;
        System.out.println("Which employee do you want to delete?");
        displayEmployee();
        login = sc.nextLine();
        for (User user : usersList) {
            if (login.equals(user.getLogin())) {
                usersList.remove(user);
                System.out.println("Success!");
                break;
            }
        }
        Statement stmt = null;
        Connection connection = MySQL.connection();
        try {
            stmt = connection.createStatement();
            String sql;
            sql = String.format("DELETE FROM `users` WHERE `users`.`login` = '%s'", login);
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

    private void displayEmployee() {
        usersList.stream()
                .filter(user -> Role.EMPLOYEE.equals(user.getRole()))
                .forEach(System.out::println);
    }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }

    @Override
    public String getActionName() {
        return "Remove Employee";
    }

    @Override
    public void execute() {
        employeeRemove();
    }

    @Override
    public String toString() {
        return "Remove Employee";
    }
}
