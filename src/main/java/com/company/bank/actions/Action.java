package com.company.bank.actions;

import com.company.bank.users.Role;

import java.util.List;
import java.util.stream.Collectors;

public interface Action {
    Role getRole();

    String getActionName();

    void execute();

    static List<Action> getActionForRoles(List<Action> actions, Role role) {
        return actions.stream()
                .filter(action -> action.getRole().equals(role))
                .collect(Collectors.toList());
    }
}
