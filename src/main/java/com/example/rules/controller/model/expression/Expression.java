package com.example.rules.controller.model.expression;

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
public class Expression {
    @NotNull
    @Valid
    private ExpressionObject object;
    @NotNull
    @Valid
    private Condition condition;
}
