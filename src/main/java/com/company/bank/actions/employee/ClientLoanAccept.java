package com.company.bank.actions.employee;

import com.company.bank.actions.Action;
import com.company.bank.database.MySQL;
import com.company.bank.loans.Loan;
import com.company.bank.utilities.AccountBalanceUtility;
import com.company.bank.utilities.LoansUtility;
import com.company.bank.users.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;

public class ClientLoanAccept implements Action {
    private Map<String, Loan> loanMap;
    private Scanner sc;
    private Map<String, Double> accountBalanceMap;

    public ClientLoanAccept(Map<String, Loan> loanMap, Scanner sc, Map<String, Double> accountBalanceMap) {
        this.loanMap = loanMap;
        this.sc = sc;
        this.accountBalanceMap = accountBalanceMap;
    }

    private void acceptLoan() {
        displayLoans();
        System.out.println("Whose loan do you want to accept?");
        String login = sc.nextLine();
        Loan userLoan = loanMap.get(login);
        if (userLoan != null) {
            userLoan.setAccepted(true);
            System.out.println("Success!");
            double dAmount = userLoan.getAmount();
            accountBalanceMap.put(login, accountBalanceMap.get(login) + dAmount);
            Statement stmt = null;
            Connection connection = MySQL.connection();
            try {
                stmt = connection.createStatement();
                String sql;
                sql = String.format("UPDATE `usersbalance` SET `balance` = '%s' WHERE `usersbalance`.`login` = '%s'",
                        String.valueOf(accountBalanceMap.get(login)), login);
                stmt.executeUpdate(sql);
                sql = String.format("UPDATE `userloans` SET `isAccepted` = 'true' WHERE `userloans`.`login` = '%s'", login);
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

    private void displayLoans() {
        for (Map.Entry<String, Loan> entry : loanMap.entrySet()) {
            if (!entry.getValue().isAccepted()) {
                String login = "|login: " + entry.getKey();
                String nameAndSurname = "|name and surname: " + entry.getValue().getName() + " " + entry.getValue().getSurname();
                String amount = "|amount: " + entry.getValue().getAmount();
                String pesel = "|pesel: " + entry.getValue().getPesel() + '|';
                System.out.format("%-15s %-35s %-15s %-20s\n", login, nameAndSurname, amount, pesel);
            }
        }

    }

    @Override
    public Role getRole() {
        return Role.EMPLOYEE;
    }

    @Override
    public String getActionName() {
        return "Accept loan request";
    }

    @Override
    public void execute() {
        acceptLoan();
    }
}
