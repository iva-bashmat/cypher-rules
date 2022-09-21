package com.example.rules.cypher.condition.handler.impl;

import com.example.rules.cypher.condition.handler.ConditionPropertyHandler;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortPropertyHandler implements ConditionPropertyHandler {
    private String portEntityName;
    private String portPropertyName;

    @Override
    public String getEntityName() {
        return portEntityName;
    }

    @Override
    public String getEntityPropertyName() {
        return portPropertyName;
    }
}
