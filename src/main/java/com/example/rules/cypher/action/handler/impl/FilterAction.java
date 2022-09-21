package com.example.rules.cypher.action.handler.impl;

import com.example.rules.cypher.action.handler.ActionHandler;
import lombok.AllArgsConstructor;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Expression;
import org.neo4j.cypherdsl.core.Node;

@AllArgsConstructor
public class FilterAction implements ActionHandler {
    private final Integer state;

    @Override
    public Expression apply(Node route) {
        return route.property("state").to(Cypher.literalOf(state));
    }
}
