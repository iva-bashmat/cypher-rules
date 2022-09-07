package com.example.rules.cypher.attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AttributeEntity {
    protected final Map<String, AttributeProperty> properties;

    protected AttributeEntity() {
        properties = new HashMap<>();
    }

    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    public AttributeProperty get(String property) {
        return properties.get(property);
    }
}
