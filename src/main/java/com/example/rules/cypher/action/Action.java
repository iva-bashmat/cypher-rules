package com.example.rules.cypher.action;

import com.example.rules.cypher.action.handler.ActionHandler;
import com.example.rules.cypher.action.handler.impl.FilterAction;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Node;


public enum Action {
    // TODO Cabotage - Probably needs to be removed
    ENABLE(new FilterAction(1)), DISABLE(new FilterAction(-1)), CABOTAGE(null);

    private final ActionHandler handler;

    Action(ActionHandler handler) {
        this.handler = handler;
    }

    // TODO review if set action is always applied to route node
    public Expression getSetExpression(Node route) {
        return handler.apply(route);
    }
}
