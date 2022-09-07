package com.example.rules.cypher.attribute.impl;

import com.example.rules.cypher.attribute.AttributeEntity;
import com.example.rules.cypher.attribute.AttributeProperty;
import com.example.rules.cypher.match.impl.CountryMatch;

public class PortAttribute extends AttributeEntity {
    public PortAttribute(String portCypher) {
        properties.put("code", new AttributeProperty(portCypher + ".code"));
        properties.put("area", new AttributeProperty(portCypher + ".area"));

        var countryCypherProperty = portCypher + "Country";
        properties.put("country", new AttributeProperty(countryCypherProperty + ".name"
                , new CountryMatch(portCypher, countryCypherProperty)));
    }
}
