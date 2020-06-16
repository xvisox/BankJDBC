package com.company.bank.actions.registration;

import java.util.List;
import java.util.Scanner;

import com.company.bank.users.User;


public class Access {
    private Scanner scanner;
    private List<User> users;

    public Access(List<User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }

    public User logIn() {
        System.out.println("enter login");
        String login = scanner.nextLine();
        System.out.println("enter password");
        String password = scanner.nextLine();
        return checkCredentials(login, password);
    }

    private User checkCredentials(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}