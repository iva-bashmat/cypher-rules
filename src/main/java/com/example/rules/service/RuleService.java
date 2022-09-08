package com.example.rules.service;

import com.example.rules.controller.model.Rule;
import com.example.rules.cypher.CypherRunner;
import com.example.rules.cypher.query.CypherQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RuleService {
    private final CypherRunner cypherRunner;

    public void runRule(Rule rule) {
        var query = CypherQuery.builder(rule).build();
        cypherRunner.run(query);
    }
}
