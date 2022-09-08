package com.example.rules.cypher.query;

import com.example.rules.controller.model.Rule;
import com.example.rules.controller.model.expression.Condition;
import com.example.rules.controller.model.expression.Expression;
import com.example.rules.cypher.action.Action;
import com.example.rules.cypher.expression.CypherObjectFactory;
import com.example.rules.cypher.query.match.AdditionalMatch;
import com.example.rules.cypher.query.operator.BinaryLogicalOperator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CypherQuery {
    private String query;
    private Map<String, Object> params;

    public static CypherQueryBuilder builder(Rule rule) {
        return new CypherQueryBuilder(rule);
    }

    public static class CypherQueryBuilder {
        private final CypherObjectFactory attributeFactory;
        private final Set<String> matches;
        private final List<CypherQueryWhere> where;
        private String action;
        private final Map<String, Object> params;

        private CypherQueryBuilder(Rule rule) {
            attributeFactory = CypherObjectFactory.getInstance();
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

        private void addExpression(Expression expression) {
            addExpression(BinaryLogicalOperator.AND, expression);
        }

        public void addExpression(BinaryLogicalOperator logicalOperator, Expression expression) {
            var attribute = attributeFactory.getAttributeProperty(expression.getObject().getType()
                    , expression.getObject().getAttribute());

            attribute.getAdditionalMatch().map(AdditionalMatch::getMatchStatement).ifPresent(matches::add);
            addExpressionWhere(logicalOperator, attribute.getCypherAttributeName(), expression.getCondition());
        }

        private void addExpressionWhere(BinaryLogicalOperator logicalOperator, String cypherProperty, Condition condition) {
            var paramName = cypherProperty.replace(".", "_") + "_" + params.size();
            where.add(new CypherQueryWhere(logicalOperator,
                    cypherProperty + condition.getOperator().getCypherOperator() + "$" + paramName));
            params.put(paramName, condition.getValue());
        }

        private void addAction(Action action) {
            var actionHandler = action.getActionHandler();

            this.action = actionHandler.getActionCypher();
            actionHandler.getAdditionalMatches().stream().map(AdditionalMatch::getMatchStatement).forEach(matches::add);
            actionHandler.getWhere().ifPresent(where::add);
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
