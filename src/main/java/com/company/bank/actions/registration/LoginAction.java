package com.company.bank.actions.registration;

import com.company.bank.users.User;

import java.util.List;
import java.util.Scanner;

public class LoginAction {
    public Scanner scanner;
    private List<User> users;
    private User sessionUser = null;

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    public LoginAction(Scanner scanner, List<User> users) {
        this.scanner = scanner;
        this.users = users;
    }

    public void execute() {
        Access access = new Access(users, scanner);
        while (sessionUser == null) {
            sessionUser = access.logIn();
        }
    }
}