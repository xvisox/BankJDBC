package com.company.bank.users;

import java.util.Formatter;

public class User {
    private String login;
    private String password;
    private Role role;
    private boolean isAccepted;

    public User() {
    }

    public User(String login, String password, Role role, boolean isAccepted) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.isAccepted = isAccepted;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        String login1 = "login: " + login;
        String password1 = "password: " + password;
        String role1 = "role: " + role;
        Formatter formatter = new Formatter();
        formatter.format("%-20s %-30s %-15s", login1, password1, role1);
        return formatter.toString();
    }
}
