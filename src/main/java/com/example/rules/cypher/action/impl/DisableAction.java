package com.example.rules.cypher.action.impl;

import com.example.rules.cypher.action.ActionHandler;

public class DisableAction extends ActionHandler {
    public DisableAction() {
        super("route.state = -1");
    }
}
