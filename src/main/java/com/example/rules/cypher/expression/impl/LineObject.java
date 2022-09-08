package com.example.rules.cypher.expression.impl;

import com.example.rules.cypher.expression.CypherAttribute;
import com.example.rules.cypher.expression.CypherObject;
import com.example.rules.cypher.query.match.impl.LineMatch;

import static com.example.rules.cypher.expression.ObjectAttribute.BUSINESS_UNIT;
import static com.example.rules.cypher.expression.ObjectAttribute.NAME;

public class LineObject extends CypherObject {
    public LineObject() {
        attributes.put(NAME, new CypherAttribute("line.name", new LineMatch()));
        attributes.put(BUSINESS_UNIT, new CypherAttribute("line.businessUnit", new LineMatch()));
    }
}
