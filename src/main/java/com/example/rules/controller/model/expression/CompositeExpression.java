package com.example.rules.controller.model.expression;

import com.example.rules.cypher.query.operator.BinaryLogicalOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositeExpression {
    @NotNull
    private BinaryLogicalOperator operator;

    @NotNull
    @Valid
    private Expression expression;
}
