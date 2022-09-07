package com.example.rules.controller.model;

public enum ConditionOperator {
    EQUALS("="), NOT_EQUALS("!=");
    private final String cypherOperator;

    ConditionOperator(String cypherOperator) {
        this.cypherOperator = cypherOperator;
    }

    public String getCypherOperator() {
        return cypherOperator;
    }
}
