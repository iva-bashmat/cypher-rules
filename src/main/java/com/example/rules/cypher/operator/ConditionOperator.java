package com.example.rules.cypher.operator;

import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Expression;

import java.util.function.BiFunction;

public enum ConditionOperator {
    EQUALS(Expression::isEqualTo), NOT_EQUALS(Expression::isNotEqualTo);

    private final BiFunction<Expression, Expression, Condition> function;

    ConditionOperator(BiFunction<Expression, Expression, Condition> function) {
        this.function = function;
    }

    public Condition apply(Expression left, Expression right) {
        return function.apply(left, right);
    }
}
