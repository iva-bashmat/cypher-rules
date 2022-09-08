package com.example.rules.cypher.expression;

import java.util.HashMap;
import java.util.Map;

public abstract class CypherObject {
    protected final Map<ObjectAttribute, CypherAttribute> attributes;

    protected CypherObject() {
        attributes = new HashMap<>();
    }

    public CypherAttribute get(ObjectAttribute attribute) {
        return attributes.get(attribute);
    }
}
