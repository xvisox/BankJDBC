package com.company.bank.utilities;

import com.company.bank.users.User;

import java.util.List;

public class PasswordUtility {

    public static boolean passCheck(String password) {
        boolean valid = true;
        if (password.length() < 8) {
            System.out.println("Password is not eight characters long.");
            valid = false;
        }
        String upperCase = "(.*[A-Z].*)";
        if (!password.matches(upperCase)) {
            System.out.println("Password must contain at least one capital letter.");
            valid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            System.out.println("Password must contain at least one number.");
            valid = false;
        }
        String specialChars = "(.*[ ! # @ $ % ^ & * ( ) - _ = + [ ] ; : ' \" , < . > / ?].*)";
        if (!password.matches(specialChars)) {
            System.out.println("Password must contain at least one special character.");
            valid = false;
        }
        String space = "(.*[   ].*)";
        if (password.matches(space)) {
            System.out.println("Password cannot contain a space.");
            valid = false;
        }
        if (password.startsWith("?")) {
            System.out.println("Password cannot start with '?'.");
            valid = false;

        }
        if (password.startsWith("!")) {
            System.out.println("Password cannot start with '!'.");
            valid = false;
        }
        return valid;
    }

    public static boolean isAlreadyTaken(String login, List<User> usersList) {
        for (User user : usersList) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

}
