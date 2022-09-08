package com.example.rules.controller.model.expression;

import com.example.rules.cypher.expression.ObjectAttribute;
import com.example.rules.cypher.expression.ObjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionObject {
    @NotNull
    private ObjectType type;
    @NotNull
    private ObjectAttribute attribute;
}
