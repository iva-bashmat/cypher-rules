package com.example.rules.cypher;

import com.example.rules.controller.model.Rule;
import com.example.rules.controller.model.RuleCondition;
import com.example.rules.cypher.action.Action;
import com.example.rules.cypher.condition.ConditionProperty;
import com.example.rules.cypher.operator.ConditionOperator;
import org.junit.jupiter.api.Test;
import org.neo4j.cypherdsl.core.Cypher;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CypherQueryDSLTest {

    @Test
    public void testBuildPodCode() {
        var rule = Rule.builder()
                .name("pod-code")
                .condition(RuleCondition.builder()
                        .propertyName(ConditionProperty.POD_CODE)
                        .propertyValue("testValue")
                        .operator(ConditionOperator.EQUALS).build())
                .action(Action.ENABLE)
                .build();

        var statement = new CypherStatementBuilder(rule).build();
        String expected = "MATCH (pol:`Port`)-[:`POL`]->(route:`Route`)<-[:`POD`]-(pod:`Port`) " +
                "WHERE (pod.code = $pod_code_0 AND route.state = 0) " +
                "SET route.state = 1 " +
                "RETURN route";
        assertEquals(expected, statement.getCypher());
        assertEquals(1, statement.getParameters().size());
        assertEquals(rule.getCondition().getPropertyValue(), statement.getParameters().get("pod_code_0"));
    }

    @Test
    public void testBuildPolArea() {
        var rule = Rule.builder()
                .name("pol-area")
                .condition(RuleCondition.builder()
                        .propertyName(ConditionProperty.POL_AREA)
                        .propertyValue("testValue")
                        .operator(ConditionOperator.NOT_EQUALS).build())
                .action(Action.DISABLE)
                .build();

        var statement = new CypherStatementBuilder(rule).build();
        String expected = "MATCH (pol:`Port`)-[:`POL`]->(route:`Route`)<-[:`POD`]-(pod:`Port`) " +
                "WHERE (pol.area <> $pod_code_0 AND route.state = 0) " +
                "SET route.state = 0 " +
                "RETURN route";
        assertEquals(expected, statement.getCypher());
        assertEquals(1, statement.getParameters().size());
        assertEquals(rule.getCondition().getPropertyValue(), statement.getParameters().get("pod_code_0"));
    }

    @Test
    public void testPodCode() {
        var portCode = "testValue";
        var portCodeParam = Cypher.parameter("pod_code_0").withValue(portCode);

        var route = Cypher.node("Route").named("route");
        var pol = Cypher.node("Port").named("pol");
        var pod = Cypher.node("Port").named("pod");

        var statement = Cypher
                .match(pol.relationshipTo(route, "POL").relationshipFrom(pod, "POD"))
                .where(route.property("state").isEqualTo(Cypher.literalOf(0))
                        .and(pod.property("code").isEqualTo(portCodeParam)))
                .set(route.property("state").to(Cypher.literalOf(1)))
                .returning(route)
                .build();

        String expected = "MATCH (pol:`Port`)-[:`POL`]->(route:`Route`)<-[:`POD`]-(pod:`Port`) " +
                "WHERE (route.state = 0 AND pod.code = $pod_code_0) " +
                "SET route.state = 1 " +
                "RETURN route";
        assertEquals(expected, statement.getCypher());
        assertEquals(1, statement.getParameters().size());
        assertEquals(portCode, statement.getParameters().get("pod_code_0"));
    }

    @Test
    public void testPodCodeMany() {
        var portCode1 = "testValue";
        var portCode1Param = Cypher.parameter("pod_code_0").withValue(portCode1);
        var portCode2 = "testValue2";
        var portCode2Param = Cypher.parameter("pod_code_1").withValue(portCode2);

        var route = Cypher.node("Route").named("route");
        var pol = Cypher.node("Port").named("pol");
        var pod = Cypher.node("Port").named("pod");

        var statement = Cypher
                .match(pol.relationshipTo(route, "POL").relationshipFrom(pod, "POD"))
                .where(
                        pod.property("code").isEqualTo(portCode1Param)
                                .or(pod.property("code").isEqualTo(portCode2Param))
                                .and(route.property("state").isEqualTo(Cypher.literalOf(0)))

                )
                .set(route.property("state").to(Cypher.literalOf(1)))
                .returning(route)
                .build();

        String expected = "MATCH (pol:`Port`)-[:`POL`]->(route:`Route`)<-[:`POD`]-(pod:`Port`) " +
                "WHERE ((pod.code = $pod_code_0 OR pod.code = $pod_code_1) AND route.state = 0) " +
                "SET route.state = 1 " +
                "RETURN route";
        assertEquals(expected, statement.getCypher());
        assertEquals(2, statement.getParameters().size());
        assertEquals(portCode1, statement.getParameters().get("pod_code_0"));
        assertEquals(portCode2, statement.getParameters().get("pod_code_1"));
    }

    @Test
    public void testPodCountry() {
        var country = "IL";
        var countryParam = Cypher.parameter("podCountry_name_0").withValue(country);

        var route = Cypher.node("Route").named("route");
        var pol = Cypher.node("Port").named("pol");
        var pod = Cypher.node("Port").named("pod");
        var podCountry = Cypher.node("Country").named("podCountry");
        var statement = Cypher
                .match(pol.relationshipTo(route, "POL").relationshipFrom(pod, "POD"))
                .match(pod.relationshipTo(podCountry, "IN"))
                .where(route.property("state").isEqualTo(Cypher.literalOf(0))
                        .and(podCountry.property("name").isEqualTo(countryParam)))
                .set(route.property("state").to(Cypher.literalOf(1)))
                .returning(route)
                .build();

        String expected = "MATCH (pol:`Port`)-[:`POL`]->(route:`Route`)<-[:`POD`]-(pod:`Port`) " +
                "MATCH (pod)-[:`IN`]->(podCountry:`Country`) " +
                "WHERE (route.state = 0 AND podCountry.name = $podCountry_name_0) " +
                "SET route.state = 1 " +
                "RETURN route";
        assertEquals(expected, statement.getCypher());
        assertEquals(1, statement.getParameters().size());
        assertEquals(country, statement.getParameters().get("podCountry_name_0"));
    }

    @Test
    public void testLineName() {
        var lineName = "Test-Line";
        var lineParam = Cypher.parameter("line_name_0").withValue(lineName);


        var route = Cypher.node("Route").named("route");
        var pol = Cypher.node("Port").named("pol");
        var pod = Cypher.node("Port").named("pod");
        var line = Cypher.node("Line").named("line");
        var lineNameProperty = Cypher.name("lineName");

        var statement = Cypher
                .match(pol.relationshipTo(route, "POL").relationshipFrom(pod, "POD"))
                .unwind(route.property("lines")).as(lineNameProperty)
                .with(Cypher.asterisk())
                .match(line.withProperties("name", lineNameProperty))
                .where(route.property("state").isEqualTo(Cypher.literalOf(0))
                        .and(line.property("name").isNotEqualTo(lineParam)))
                .set(route.property("state").to(Cypher.literalOf(1)))
                .returning(route)
                .build();

        String expected = "MATCH (pol:`Port`)-[:`POL`]->(route:`Route`)<-[:`POD`]-(pod:`Port`) " +
                "UNWIND route.lines AS lineName " +
                "WITH * " +
                "MATCH (line:`Line` {name: lineName}) " +
                "WHERE (route.state = 0 AND line.name <> $line_name_0) " +
                "SET route.state = 1 " +
                "RETURN route";
        assertEquals(expected, statement.getCypher());
        assertEquals(1, statement.getParameters().size());
        assertEquals(lineName, statement.getParameters().get("line_name_0"));
    }
}
