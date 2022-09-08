package com.example.rules.cypher.action.impl;

import com.example.rules.cypher.action.ActionHandler;
import com.example.rules.cypher.expression.CypherObjectFactory;
import com.example.rules.cypher.query.CypherQueryWhere;

import static com.example.rules.cypher.expression.ObjectAttribute.COUNTRY;
import static com.example.rules.cypher.expression.ObjectType.POD;
import static com.example.rules.cypher.expression.ObjectType.POL;
import static com.example.rules.cypher.query.operator.BinaryLogicalOperator.AND;

public class CabotageAction extends ActionHandler {
    public CabotageAction() {
        super("route.state = -1");
        var objectFactory = CypherObjectFactory.getInstance();

        var polCountry = objectFactory.getAttributeProperty(POL, COUNTRY).getAdditionalMatch().orElseThrow(() -> new IllegalStateException("No configuration for POL.country"));
        var podCountry = objectFactory.getAttributeProperty(POD, COUNTRY).getAdditionalMatch().orElseThrow(() -> new IllegalStateException("No configuration for POD.country"));

        addAdditionalMatch(polCountry);
        addAdditionalMatch(podCountry);

        setWhere(CypherQueryWhere.builder().operator(AND).where(String.format("%s=%s", polCountry.getMatchedPropertyName(), podCountry.getMatchedPropertyName())).build());
    }
}
