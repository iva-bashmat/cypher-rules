package com.example.rules.cypher.expression;

import com.example.rules.cypher.expression.impl.LineObject;
import com.example.rules.cypher.expression.impl.PortObject;

import java.util.HashMap;
import java.util.Map;

public final class CypherObjectFactory {
    private static final CypherObjectFactory INSTANCE = new CypherObjectFactory();
    private final Map<ObjectType, CypherObject> attributes;

    private CypherObjectFactory() {
        attributes = new HashMap<>();
        attributes.put(ObjectType.POL, new PortObject("pol"));
        attributes.put(ObjectType.POD, new PortObject("pod"));
        attributes.put(ObjectType.LINE, new LineObject());
    }

    public CypherAttribute getAttributeProperty(ObjectType attributeType, ObjectAttribute property) {
        return attributes.get(attributeType).get(property);
    }

    public static CypherObjectFactory getInstance() {
        return CypherObjectFactory.INSTANCE;
    }


}
