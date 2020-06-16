package com.company.bank.utilities;

import com.company.bank.database.MySQL;
import com.company.bank.loans.Loan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class LoansUtility {

    public static Map<String, Loan> loadLoans() {
        Map<String, Loan> loanMap = new HashMap<>();
        Statement stmt = null;
        Connection connection = MySQL.connection();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM userloans");
            while (rs.next()) {
                //Retrieve by column name
                String login = rs.getString("login");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                long pesel = rs.getLong("pesel");
                int amount = rs.getInt("amount");
                boolean isAccepted = rs.getBoolean("isAccepted");
                boolean isPaid = rs.getBoolean("isPaid");
                Loan loan = new Loan(name, surname, pesel, amount, isAccepted, isPaid);
                loanMap.put(login, loan);
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
        return loanMap;
    }


}
