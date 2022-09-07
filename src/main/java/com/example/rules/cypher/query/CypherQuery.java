package com.example.rules.cypher.query;

import com.example.rules.controller.model.*;
import com.example.rules.cypher.AttributeFactory;
import com.example.rules.cypher.attribute.AttributeProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CypherQuery {
    private String query;
    private Map<String, Object> params;

    public static CypherQueryBuilder builder(FilterRule rule) {
        return new CypherQueryBuilder(rule);
    }

    public static class CypherQueryBuilder {
        private final AttributeFactory attributeFactory;
        private final Set<String> matches;
        private final List<CypherQueryWhere> where;
        private String action;
        private final Map<String, Object> params;

        private CypherQueryBuilder(FilterRule rule) {
            attributeFactory = new AttributeFactory();
            matches = new HashSet<>();
            where = new ArrayList<>();
            params = new HashMap<>();

            addExpression(rule.getExpression());
            addAction(rule.getAction());

            if (rule.getCompositeExpressions() != null) {
                rule.getCompositeExpressions()
                        .forEach(compositeExpression -> addExpression(compositeExpression.getOperator(), compositeExpression.getExpression()));
            }
        }

        private CypherQueryBuilder addExpression(Expression expression) {
            return addExpression(BinaryLogicalOperator.AND, expression);
        }

        public CypherQueryBuilder addExpression(BinaryLogicalOperator logicalOperator, Expression expression) {
            var attributeProperty = attributeFactory.getAttributeProperty(expression.getAttribute().getType()
                    , expression.getAttribute().getProperty());

            addMatch(attributeProperty);
            addWhere(logicalOperator, attributeProperty.getCypherProperty(), expression.getCondition());

            return this;
        }

        private void addMatch(AttributeProperty attributeProperty) {
            attributeProperty.getMatch().getAdditionalMatch().ifPresent(matches::add);
        }

        private void addWhere(BinaryLogicalOperator logicalOperator, String cypherProperty, Condition condition) {
            var paramName = cypherProperty.replace(".", "_") + "_" + params.size();
            where.add(new CypherQueryWhere(logicalOperator,
                    cypherProperty + condition.getOperator().getCypherOperator() + "$" + paramName));
            params.put(paramName, condition.getValue());
        }

        private CypherQueryBuilder addAction(FilterRuleAction ruleAction) {
            action = ruleAction.getCypherUpdate();
            return this;
        }

        public CypherQuery build() {
            var builder = new StringBuilder();
            builder.append("MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)").append(System.lineSeparator());
            matches.forEach(match -> builder.append(match).append(System.lineSeparator()));

            builder.append("WHERE route.state = 0").append(System.lineSeparator());
            where.forEach(where -> builder.append(where.getOperator().name()).append(" ").append(where.getWhere()).append(System.lineSeparator()));

            builder.append("SET ").append(action);
            return new CypherQuery(builder.toString(), params);
        }
    }
}
