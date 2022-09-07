package com.example.rules.cypher.attribute;

import com.example.rules.cypher.match.AttributeMatch;
import com.example.rules.cypher.match.impl.EmptyMatch;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttributeProperty {
    private String cypherProperty;
    private AttributeMatch match;

    public AttributeProperty(String cypherProperty) {
        this.cypherProperty = cypherProperty;
        match = new EmptyMatch();
    }
}
