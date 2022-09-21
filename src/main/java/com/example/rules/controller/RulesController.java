package com.example.rules.controller;

import com.example.rules.service.RuleService;
import com.networknt.schema.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class RulesController {

    private final RuleService ruleService;

    @PostMapping("/run")
    public void runRule(@RequestBody @Valid String body) {
        ruleService.runRule(body);
    }

    @PostMapping("/validate")
    public Set<ValidationMessage> validate(@RequestBody String body) {
        return ruleService.validate(body);
    }
}
