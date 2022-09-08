package com.example.rules.controller.model;

import com.example.rules.controller.model.expression.CompositeExpression;
import com.example.rules.controller.model.expression.Expression;
import com.example.rules.cypher.action.Action;
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
public class Rule {
    @NotEmpty
    private String name;
    @NotNull
    @Valid
    private Expression expression;
    @Valid
    private List<CompositeExpression> compositeExpressions;
    @NotNull
    private Action action;

}
