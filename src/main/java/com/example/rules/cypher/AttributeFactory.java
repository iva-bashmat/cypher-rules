package com.example.rules.cypher;

import com.example.rules.controller.model.AttributeType;
import com.example.rules.cypher.attribute.AttributeEntity;
import com.example.rules.cypher.attribute.AttributeProperty;
import com.example.rules.cypher.attribute.impl.LineAttribute;
import com.example.rules.cypher.attribute.impl.PortAttribute;

import java.util.HashMap;
import java.util.Map;

public class AttributeFactory {

    private final Map<AttributeType, AttributeEntity> attributes;

    public AttributeFactory() {
        attributes = new HashMap<>();
        attributes.put(AttributeType.POL, new PortAttribute("pol"));
        attributes.put(AttributeType.POD, new PortAttribute("pod"));
        attributes.put(AttributeType.LINE, new LineAttribute());
    }

    public AttributeProperty getAttributeProperty(AttributeType attributeType, String property) {
        return attributes.get(attributeType).get(property);
    }


}
