package com.example.rules.controller;

import com.example.rules.controller.model.FilterRule;
import com.example.rules.service.RuleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rules")
@AllArgsConstructor
public class RulesController {

    private final RuleService ruleService;
    
    @PostMapping("/run")
    public void runRule(@RequestBody @Valid FilterRule rule) {
        ruleService.runRule(rule);
    }

}
