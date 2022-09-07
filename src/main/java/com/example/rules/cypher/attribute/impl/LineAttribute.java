package com.example.rules.cypher.attribute.impl;

import com.example.rules.cypher.attribute.AttributeEntity;
import com.example.rules.cypher.attribute.AttributeProperty;
import com.example.rules.cypher.match.impl.LineMatch;

public class LineAttribute extends AttributeEntity {
    public LineAttribute() {
        properties.put("name", new AttributeProperty("line.name", new LineMatch()));
        properties.put("businessUnit", new AttributeProperty("line.businessUnit", new LineMatch()));
    }
}
