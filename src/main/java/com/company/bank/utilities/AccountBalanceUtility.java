package com.company.bank.utilities;


import com.company.bank.database.MySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class AccountBalanceUtility {

    public static Map<String, Double> loadBalance() {
        Map<String, Double> balanceMap = new HashMap<>();
        Statement stmt = null;
        Connection connection = MySQL.connection();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usersbalance");
            while (rs.next()) {
                //Retrieve by column name
                String login = rs.getString("login");
                String doubleString = rs.getString("balance");
                Double balance = Double.parseDouble(doubleString);
                balanceMap.put(login, balance);
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
        return balanceMap;
    }

}
