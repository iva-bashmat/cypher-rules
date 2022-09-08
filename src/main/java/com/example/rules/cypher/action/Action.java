package com.example.rules.cypher.action;

import com.example.rules.cypher.action.impl.CabotageAction;
import com.example.rules.cypher.action.impl.DisableAction;
import com.example.rules.cypher.action.impl.EnableAction;

public enum Action {
    ENABLE(EnableAction.class), DISABLE(DisableAction.class), CABOTAGE(CabotageAction.class);

    private final ActionHandler actionHandler;

    Action(Class<? extends ActionHandler> actionHandlerClass) {
        try {
            actionHandler = actionHandlerClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Action Handler Class cannot be instantiated");
        }
    }

    public ActionHandler getActionHandler() {
        return actionHandler;
    }
}
