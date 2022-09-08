package com.example.rules.cypher.action.impl;

import com.example.rules.cypher.action.ActionHandler;

public class EnableAction extends ActionHandler {
    public EnableAction() {
        super("route.state = 1");
    }
}
