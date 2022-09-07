package com.example.rules.cypher;


import com.example.rules.controller.model.*;
import com.example.rules.cypher.query.CypherQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CypherQueryTest {
    @Test
    public void testPodCode() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.POD, "code"))
                .condition(new Condition("testValue", ConditionOperator.EQUALS))
                .build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .action(FilterRuleAction.ENABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "WHERE route.state = 0\n" +
                "AND pod.code=$pod_code_0\n" +
                "SET route.state = 1";
        assertEquals(expected, query.getQuery());
        assertEquals(1, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("pod_code_0"));
    }

    @Test
    public void testPodArea() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.POD, "area"))
                .condition(new Condition("A1", ConditionOperator.NOT_EQUALS))
                .build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .action(FilterRuleAction.DISABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "WHERE route.state = 0\n" +
                "AND pod.area!=$pod_area_0\n" +
                "SET route.state = -1";
        assertEquals(expected, query.getQuery());
        assertEquals(1, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("pod_area_0"));
    }

    @Test
    public void testPodCountry() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.POD, "country"))
                .condition(new Condition("IL", ConditionOperator.EQUALS))
                .build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .action(FilterRuleAction.ENABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "MATCH (pod)-[:IN]->(podCountry:Country)\n" +
                "WHERE route.state = 0\n" +
                "AND podCountry.name=$podCountry_name_0\n" +
                "SET route.state = 1";
        assertEquals(expected, query.getQuery());
        assertEquals(1, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("podCountry_name_0"));
    }

    @Test
    public void testPolCode() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.POL, "code"))
                .condition(new Condition("testValue", ConditionOperator.EQUALS))
                .build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .action(FilterRuleAction.DISABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "WHERE route.state = 0\n" +
                "AND pol.code=$pol_code_0\n" +
                "SET route.state = -1";
        assertEquals(expected, query.getQuery());
        assertEquals(1, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("pol_code_0"));
    }

    @Test
    public void testPodCodeMultiple() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.POD, "code"))
                .condition(new Condition("testValue", ConditionOperator.EQUALS))
                .build();
        var compositeExpression = CompositeExpression.builder()
                .operator(BinaryLogicalOperator.OR)
                .expression(Expression.builder()
                        .attribute(new Attribute(AttributeType.POD, "code"))
                        .condition(new Condition("testValue2", ConditionOperator.EQUALS))
                        .build()).build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .compositeExpressions(List.of(compositeExpression))
                .action(FilterRuleAction.DISABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "WHERE route.state = 0\n" +
                "AND pod.code=$pod_code_0\n" +
                "OR pod.code=$pod_code_1\n" +
                "SET route.state = -1";
        assertEquals(expected, query.getQuery());
        assertEquals(2, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("pod_code_0"));
        assertEquals(compositeExpression.getExpression().getCondition().getValue(), query.getParams().get("pod_code_1"));
    }

    @Test
    public void testPodCountryMultiple() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.POD, "country"))
                .condition(new Condition("IL", ConditionOperator.EQUALS))
                .build();
        var compositeExpression = CompositeExpression.builder()
                .operator(BinaryLogicalOperator.OR)
                .expression(Expression.builder()
                        .attribute(new Attribute(AttributeType.POD, "country"))
                        .condition(new Condition("ES", ConditionOperator.EQUALS))
                        .build()).build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .compositeExpressions(List.of(compositeExpression))
                .action(FilterRuleAction.DISABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "MATCH (pod)-[:IN]->(podCountry:Country)\n" +
                "WHERE route.state = 0\n" +
                "AND podCountry.name=$podCountry_name_0\n" +
                "OR podCountry.name=$podCountry_name_1\n" +
                "SET route.state = -1";
        assertEquals(expected, query.getQuery());
        assertEquals(2, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("podCountry_name_0"));
        assertEquals(compositeExpression.getExpression().getCondition().getValue(), query.getParams().get("podCountry_name_1"));
    }

    @Test
    public void testPodPolCountry() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.POD, "country"))
                .condition(new Condition("IL", ConditionOperator.EQUALS))
                .build();
        var compositeExpression = CompositeExpression.builder()
                .operator(BinaryLogicalOperator.AND)
                .expression(Expression.builder()
                        .attribute(new Attribute(AttributeType.POL, "country"))
                        .condition(new Condition("ES", ConditionOperator.EQUALS))
                        .build()).build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .compositeExpressions(List.of(compositeExpression))
                .action(FilterRuleAction.ENABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "MATCH (pod)-[:IN]->(podCountry:Country)\n" +
                "MATCH (pol)-[:IN]->(polCountry:Country)\n" +
                "WHERE route.state = 0\n" +
                "AND podCountry.name=$podCountry_name_0\n" +
                "AND polCountry.name=$polCountry_name_1\n" +
                "SET route.state = 1";
        assertEquals(expected, query.getQuery());
        assertEquals(2, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("podCountry_name_0"));
        assertEquals(compositeExpression.getExpression().getCondition().getValue(), query.getParams().get("polCountry_name_1"));
    }

    @Test
    public void testLineName() {
        var expression = Expression.builder()
                .attribute(new Attribute(AttributeType.LINE, "name"))
                .condition(new Condition("Test-Line", ConditionOperator.EQUALS))
                .build();
        var filterRule = FilterRule.builder()
                .expression(expression)
                .action(FilterRuleAction.ENABLE)
                .build();
        var query = CypherQuery.builder(filterRule).build();
        String expected = "MATCH (pol:Port)-[:POL]->(route:Direct)<-[:POD]-(pod:Port)\n" +
                "UNWIND route.lines as lineName MATCH (line:Line {name: lineName})\n" +
                "WHERE route.state = 0\n" +
                "AND line.name=$line_name_0\n" +
                "SET route.state = 1";
        assertEquals(expected, query.getQuery());
        assertEquals(1, query.getParams().size());
        assertEquals(expression.getCondition().getValue(), query.getParams().get("line_name_0"));
    }

}
