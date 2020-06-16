package com.company.bank;

import com.company.bank.utilities.AccountBalanceUtility;
import com.company.bank.utilities.LoansUtility;
import com.company.bank.utilities.UserUtility;
import com.company.bank.actions.Action;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.actions.registration.SignUp;
import com.company.bank.loans.Loan;
import com.company.bank.users.Role;
import com.company.bank.users.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        //menu
        Scanner sc = new Scanner(System.in);
        List<User> usersList = UserUtility.loadUsers();
        Map<String, Double> balanceMap = AccountBalanceUtility.loadBalance();
        Map<String, Loan> loanMap = LoansUtility.loadLoans();
        List<Action> actions = new ArrayList<>();
        LoginAction loginAction = new LoginAction(sc, usersList);
        SignUp signUp = new SignUp(usersList, sc);
        Initializer initializer = new Initializer.Builder()
                .withSc(sc)
                .withActionList(actions)
                .withBalanceMap(balanceMap)
                .withLoanMap(loanMap)
                .withLoginAction(loginAction)
                .withUsersList(usersList)
                .build();
        initializer.init();

        User sessionUser = mainMenuUser(sc, loginAction, signUp);
        //actions
        if (sessionUser != null && sessionUser.isAccepted()) {
            String command;
            Role sessionAccess = sessionUser.getRole();
            List<Action> sessionActions = Action.getActionForRoles(actions, sessionAccess);

            System.out.println("Logged as " + sessionAccess);
            while (true) {
                System.out.println("To continue press enter, if you want to leave type 'exit'");
                command = sc.nextLine();
                if (command.equals("exit")) {
                    break;
                }
                displayAndExecute(sessionActions, sc);
            }
        } else {
            System.out.println("Check your account later...");
        }
    }

    private static User mainMenuUser(Scanner sc, LoginAction loginAction, SignUp signUp) {
        System.out.println("Choose action:\n0 Sign Up\n1 Log In");
        String commandStr = sc.nextLine();
        int command = Integer.parseInt(commandStr);
        switch (command) {
            case 0:
                signUp.execute();
                return null;
            case 1:
                loginAction.execute();
                return loginAction.getSessionUser();
            default:
                return null;
        }
    }

    private static void displayAndExecute(List<Action> actions, Scanner sc) {
        String actionNumberStr;
        int actionNumber;
        System.out.println("Choose action: ");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println(i + ". " + actions.get(i).getActionName());
        }
        actionNumberStr = sc.nextLine();
        actionNumber = Integer.parseInt(actionNumberStr);
        while (actionNumber >= actions.size()) {
            System.out.println("Try Again");
            actionNumberStr = sc.nextLine();
            actionNumber = Integer.parseInt(actionNumberStr);
        }
        actions.get(actionNumber).execute();
    }
}
