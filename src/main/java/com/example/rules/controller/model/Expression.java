package com.example.rules.controller.model;

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
    private Attribute attribute;
    @NotNull
    @Valid
    private Condition condition;
}
