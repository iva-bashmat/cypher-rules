package com.example.rules.controller.model.expression;

import com.example.rules.cypher.query.operator.ConditionOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
    @NotEmpty
    private String value;
    @NotNull
    private ConditionOperator operator;
}
