package com.example.rules.controller.model;

public enum FilterRuleAction {
    ENABLE("route.state = 1"), DISABLE("route.state = -1");

    private final String cypherUpdate;

    FilterRuleAction(String cypherUpdate) {
        this.cypherUpdate = cypherUpdate;
    }

    public String getCypherUpdate() {
        return cypherUpdate;
    }
}
