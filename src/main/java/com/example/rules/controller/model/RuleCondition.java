package com.example.rules.controller.model;

import com.example.rules.cypher.condition.ConditionProperty;
import com.example.rules.cypher.operator.ConditionOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleCondition {
    private ConditionProperty propertyName;
    private String propertyValue;
    private ConditionOperator operator;
}
