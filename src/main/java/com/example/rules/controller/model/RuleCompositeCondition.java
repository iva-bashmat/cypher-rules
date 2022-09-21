package com.example.rules.controller.model;

import com.example.rules.cypher.operator.BinaryLogicalOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleCompositeCondition {
    private BinaryLogicalOperator operator;
    private RuleCondition condition;
}
