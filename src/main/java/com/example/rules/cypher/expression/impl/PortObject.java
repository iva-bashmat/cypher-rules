package com.example.rules.cypher.expression.impl;

import com.example.rules.cypher.expression.CypherAttribute;
import com.example.rules.cypher.expression.CypherObject;
import com.example.rules.cypher.query.match.impl.CountryMatch;

import static com.example.rules.cypher.expression.ObjectAttribute.*;

public class PortObject extends CypherObject {
    public PortObject(String portCypher) {
        attributes.put(CODE, new CypherAttribute(portCypher + ".code"));
        attributes.put(AREA, new CypherAttribute(portCypher + ".area"));

        var countryMatch = new CountryMatch(portCypher);
        attributes.put(COUNTRY, new CypherAttribute(countryMatch.getMatchedPropertyName() + ".name", countryMatch));
    }
}
