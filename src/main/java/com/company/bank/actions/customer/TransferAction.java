package com.company.bank.actions.customer;

import com.company.bank.actions.Action;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.database.MySQL;
import com.company.bank.utilities.AccountBalanceUtility;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;

public class TransferAction implements Action {
    private Map<String, Double> balanceMap;
    private LoginAction loginAction;
    private Scanner sc;

    public TransferAction(Map<String, Double> balanceMap, LoginAction loginAction, Scanner sc) {
        this.balanceMap = balanceMap;
        this.loginAction = loginAction;
        this.sc = sc;
    }

    private void transferMoney() {
        User sessionUser = loginAction.getSessionUser();
        String sessionUserLogin = sessionUser.getLogin();
        double balanceSessionUser, balanceOfReceiver;
        if (balanceMap.get(sessionUserLogin) == 0) {
            System.out.println("You don't have money");
        } else {
            String receiverLogin = receiverLoginFunc();
            double amountToTransfer = amountToTransferFunc(sessionUserLogin);
            balanceSessionUser = balanceMap.get(sessionUserLogin) - amountToTransfer;
            balanceOfReceiver = balanceMap.get(receiverLogin) + amountToTransfer;

            balanceMap.put(sessionUserLogin, balanceSessionUser);
            balanceMap.put(receiverLogin, balanceOfReceiver);
            Statement stmt = null;
            Connection connection = MySQL.connection();
            try {
                stmt = connection.createStatement();
                String sql;
                sql = String.format("UPDATE `usersbalance` SET `balance` = '%s' WHERE `usersbalance`.`login` = '%s'",
                        balanceSessionUser, sessionUserLogin);
                stmt.executeUpdate(sql);
                sql = String.format("UPDATE `usersbalance` SET `balance` = '%s' WHERE `usersbalance`.`login` = '%s'",
                        balanceOfReceiver, receiverLogin);
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
    }

    private String receiverLoginFunc() {
        String receiverLogin;
        System.out.println("Who would you like to transfer money to?");
        receiverLogin = sc.nextLine();
        while (!isFound(receiverLogin)) {
            System.out.println("You can not transfer money to this user");
            receiverLogin = sc.nextLine();
        }
        return receiverLogin;
    }

    private double amountToTransferFunc(String sessionUserLogin) {
        double amountToTransfer;
        String amountString;
        System.out.println("How much money do you want to transfer?");
        amountString = sc.nextLine();
        amountToTransfer = Double.parseDouble(amountString);
        while (isGreaterThanBalance(amountToTransfer, sessionUserLogin)) {
            System.out.println("Too much, try again");
            amountString = sc.nextLine();
            amountToTransfer = Double.parseDouble(amountString);
        }
        return amountToTransfer;
    }

    private boolean isGreaterThanBalance(double amount, String sessionUserLogin) {
        return amount > balanceMap.get(sessionUserLogin);
    }

    private boolean isFound(String receiverLogin) {
        for (Map.Entry<String, Double> entry : balanceMap.entrySet()) {
            if (entry.getKey().equals(receiverLogin)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Role getRole() {
        return Role.CUSTOMER;
    }

    @Override
    public String getActionName() {
        return "Transfer Money";
    }

    @Override
    public void execute() {
        transferMoney();
    }
}
