package com.example.rules.cypher;

import com.example.rules.controller.model.Rule;
import com.example.rules.controller.model.RuleCondition;
import org.neo4j.cypherdsl.core.Condition;
import org.neo4j.cypherdsl.core.Cypher;
import org.neo4j.cypherdsl.core.Node;
import org.neo4j.cypherdsl.core.Statement;

import java.util.HashMap;
import java.util.Map;

public class CypherStatementBuilder {
    public static final String ROUTE = "route";
    public static final String POL = "pol";
    public static final String POD = "pod";
    private final Map<String, Node> nodes;
    private final Statement statement;

    public CypherStatementBuilder(Rule rule) {
        nodes = new HashMap<>();
        nodes.put(CypherStatementBuilder.ROUTE, Cypher.node("Route").named(CypherStatementBuilder.ROUTE));
        nodes.put(CypherStatementBuilder.POL, Cypher.node("Port").named(CypherStatementBuilder.POL));
        nodes.put(CypherStatementBuilder.POD, Cypher.node("Port").named(CypherStatementBuilder.POD));

        var statementBuilder = Cypher
                .match(getNode(CypherStatementBuilder.POL)
                        .relationshipTo(getNode(CypherStatementBuilder.ROUTE), "POL")
                        .relationshipFrom(getNode(CypherStatementBuilder.POD), "POD"));

        var condition = createWhereCondition((rule.getCondition()));
        var stateCondition = getNode(CypherStatementBuilder.ROUTE).property("state").isEqualTo(Cypher.literalOf(0));

        statement = statementBuilder
                .where(condition.and(stateCondition))
                // TODO add enable / disable actions
                .set(getNode(CypherStatementBuilder.ROUTE).property("state").to(Cypher.literalOf(1)))
                .returning(CypherStatementBuilder.ROUTE)
                .build();

    }

    private Condition createWhereCondition(RuleCondition ruleCondition) {
        var conditionHandler = ruleCondition.getPropertyName().getHandler();
        var nodePropertyExpression = getNode(conditionHandler.getEntityName()).property(conditionHandler.getEntityPropertyName());
        // TODO generate param name
        var parameterExpression = Cypher.parameter("pod_code_0").withValue(ruleCondition.getPropertyValue());
        return ruleCondition.getOperator().apply(nodePropertyExpression, parameterExpression);
    }

    private Node getNode(String name) {
        return nodes.get(name);
    }

    public Statement build() {
        return statement;
    }
}
