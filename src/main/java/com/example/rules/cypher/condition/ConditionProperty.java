package com.example.rules.cypher.condition;

import com.example.rules.cypher.condition.handler.ConditionPropertyHandler;
import com.example.rules.cypher.condition.handler.impl.PortPropertyHandler;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum ConditionProperty {
    // TODO add handlers
    POL_CODE("route.pol.code", new PortPropertyHandler("pol", "code")),
    POL_AREA("route.pol.area", new PortPropertyHandler("pol", "area")),
    POL_COUNTRY("route.pol.country", new PortPropertyHandler("pol", "country")),
    POD_CODE("route.pod.code", new PortPropertyHandler("pod", "code")),
    POD_AREA("route.pod.area", new PortPropertyHandler("pod", "area")),
    POD_COUNTRY("route.pod.country", new PortPropertyHandler("pod", "country")),
    LINE_NAME("route.line.name", new PortPropertyHandler("line", "code")),
    LINE_BUSINESS_UNIT("route.line.businessUnit", new PortPropertyHandler("line", "code"));

    private final String propertyCode;
    private final ConditionPropertyHandler handler;

    ConditionProperty(String propertyCode, ConditionPropertyHandler handler) {
        this.propertyCode = propertyCode;
        this.handler = handler;
    }

    public ConditionPropertyHandler getHandler() {
        return handler;
    }

    @JsonCreator
    public static ConditionProperty getDepartmentFromCode(String value) {
        for (ConditionProperty condition : ConditionProperty.values()) {
            if (condition.propertyCode.equals(value)) {
                return condition;
            }
        }
        return null;
    }
}
