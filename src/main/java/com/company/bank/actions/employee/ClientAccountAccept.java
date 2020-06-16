package com.company.bank.actions.employee;

import com.company.bank.actions.Action;
import com.company.bank.database.MySQL;
import com.company.bank.utilities.AccountBalanceUtility;
import com.company.bank.utilities.UserUtility;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientAccountAccept implements Action {
    private List<User> usersList;
    private Scanner sc;
    private Map<String, Double> balanceMap;

    public ClientAccountAccept(List<User> usersList, Scanner sc, Map<String, Double> balanceMap) {
        this.usersList = usersList;
        this.sc = sc;
        this.balanceMap = balanceMap;
    }

    private void accountAccept() {
        String login;
        displayCustomers();
        System.out.println("Which customer account do you want to accept?");
        login = sc.nextLine();
        User foundUser = UserUtility.findUser(usersList, login);
        if (foundUser != null) {
            System.out.println("Success, user found");
            foundUser.setAccepted(true);
            balanceMap.put(foundUser.getLogin(), 0.0);
            Statement stmt = null;
            Connection connection = MySQL.connection();
            try {
                stmt = connection.createStatement();
                String sql;
                sql = String.format("UPDATE `users` SET `isAccepted` = 'true' WHERE `login` = '%s'", login);
                stmt.executeUpdate(sql);
                sql = String.format("INSERT INTO `usersbalance` VALUES ('%s', '0.0')", login);
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
        } else {
            System.out.println("User not found");
        }
    }

    private void displayCustomers() {
        usersList.stream()
                .filter(user -> Role.CUSTOMER.equals(user.getRole()) && !user.isAccepted())
                .forEach(System.out::println);
    }

    @Override
    public Role getRole() {
        return Role.EMPLOYEE;
    }

    @Override
    public String getActionName() {
        return "Accept account request";
    }

    @Override
    public void execute() {
        accountAccept();
    }
}
