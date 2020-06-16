package com.company.bank;

import com.company.bank.actions.Action;
import com.company.bank.actions.admin.EmployeeAddAction;
import com.company.bank.actions.admin.EmployeeEdit;
import com.company.bank.actions.admin.EmployeeRemove;
import com.company.bank.actions.customer.LoanApplication;
import com.company.bank.actions.customer.TransferAction;
import com.company.bank.actions.employee.ClientAccountAccept;
import com.company.bank.actions.employee.ClientLoanAccept;
import com.company.bank.actions.registration.LoginAction;
import com.company.bank.loans.Loan;
import com.company.bank.users.User;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Initializer {
    private List<Action> actionList;
    private List<User> usersList;
    private Scanner sc;
    private Map<String, Double> balanceMap;
    private Map<String, Loan> loanMap;
    private LoginAction loginAction;

    private Initializer(Builder builder) {
        actionList = builder.actionList;
        usersList = builder.usersList;
        sc = builder.sc;
        balanceMap = builder.balanceMap;
        loanMap = builder.loanMap;
        loginAction = builder.loginAction;
    }

    public void init() {
        EmployeeAddAction employeeAddAction = new EmployeeAddAction(usersList, sc);
        EmployeeRemove employeeRemove = new EmployeeRemove(usersList, sc);
        EmployeeEdit employeeEdit = new EmployeeEdit(usersList, sc);
        ClientAccountAccept clientAccountAccept = new ClientAccountAccept(usersList, sc, balanceMap);
        LoanApplication loanApplication = new LoanApplication(loanMap, sc, loginAction);
        ClientLoanAccept clientLoanAccept = new ClientLoanAccept(loanMap, sc, balanceMap);
        TransferAction transferAction = new TransferAction(balanceMap, loginAction, sc);

        actionList.add(employeeAddAction);
        actionList.add(employeeRemove);
        actionList.add(employeeEdit);
        actionList.add(clientAccountAccept);
        actionList.add(loanApplication);
        actionList.add(clientLoanAccept);
        actionList.add(transferAction);
    }

    public static final class Builder {
        private List<Action> actionList;
        private List<User> usersList;
        private Scanner sc;
        private Map<String, Double> balanceMap;
        private Map<String, Loan> loanMap;
        private LoginAction loginAction;

        public Builder() {
        }

        public Builder withActionList(List<Action> val) {
            actionList = val;
            return this;
        }

        public Builder withUsersList(List<User> val) {
            usersList = val;
            return this;
        }

        public Builder withSc(Scanner val) {
            sc = val;
            return this;
        }

        public Builder withBalanceMap(Map<String, Double> val) {
            balanceMap = val;
            return this;
        }

        public Builder withLoanMap(Map<String, Loan> val) {
            loanMap = val;
            return this;
        }

        public Builder withLoginAction(LoginAction val) {
            loginAction = val;
            return this;
        }

        public Initializer build() {
            return new Initializer(this);
        }
    }
}
