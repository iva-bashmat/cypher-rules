package com.example.rules.cypher.action.handler;

import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Node;

public interface ActionHandler {

    Expression apply(Node route);
}
