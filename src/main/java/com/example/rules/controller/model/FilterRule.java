package com.example.rules.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRule {
    @NotEmpty
    private String name;
    @NotNull
    @Valid
    private Expression expression;
    @Valid
    private List<CompositeExpression> compositeExpressions;
    @NotNull
    private FilterRuleAction action;

}
