package com.example.rules.controller.model;

import com.example.rules.cypher.action.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule {
    private String name;
    private RuleCondition condition;
    private List<RuleCompositeCondition> compositeConditions;
    private Action action;

}
