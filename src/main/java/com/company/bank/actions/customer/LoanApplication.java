package com.company.bank.actions.customer;

import com.company.bank.actions.Action;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.database.MySQL;
import com.company.bank.loans.Loan;
import com.company.bank.utilities.LoansUtility;
import com.company.bank.users.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Scanner;

public class LoanApplication implements Action {
    private final Map<String, Loan> loanMap;
    private final Scanner sc;
    private final LoginAction loginAction;

    public LoanApplication(Map<String, Loan> loanMap, Scanner sc, LoginAction loginAction) {
        this.loanMap = loanMap;
        this.sc = sc;
        this.loginAction = loginAction;
    }

    private void applyForALoan() {
        String login = loginAction.getSessionUser().getLogin();
        if (checkUserLoans(loanMap, login)) {
            System.out.println("You can have one loan at the time");
        } else {
            Loan loan = createLoan();
            loanMap.put(login, loan);
            Statement stmt = null;
            Connection connection = MySQL.connection();
            try {
                stmt = connection.createStatement();
                String sql;
                sql = String.format("INSERT INTO `userloans` VALUES ('%s', '%s', '%s', '%s', '%s', 'false', 'false')",
                        login, loan.getName(), loan.getSurname(), loan.getPesel(), loan.getAmount());
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

    private Loan createLoan() {
        String name, surname, peselStr;
        int amount;
        long pesel;
        System.out.println("Name and Surname:");
        String[] split = sc.nextLine().split(" ");
        name = split[0];
        surname = split[1];
        System.out.println("Write down pesel:");
        peselStr = sc.nextLine();
        while (!checkPesel(peselStr)) {
            System.out.println("Try again.");
            peselStr = sc.nextLine();
        }
        pesel = Long.parseLong(peselStr);
        System.out.println("Write down amount:");
        amount = sc.nextInt();
        sc.nextLine();
        System.out.println("Your application will be considered soon.");
        return new Loan(name, surname, pesel, amount, false, false);
    }

    private boolean checkUserLoans(Map<String, Loan> loanMap, String login) {
        for (Map.Entry<String, Loan> entry : loanMap.entrySet()) {
            if (entry.getKey().equals(login)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPesel(String pesel) {
        return 11 == pesel.length();
    }

    @Override
    public Role getRole() {
        return Role.CUSTOMER;
    }

    @Override
    public String getActionName() {
        return "Loan Application";
    }

    @Override
    public void execute() {
        applyForALoan();
    }
}
