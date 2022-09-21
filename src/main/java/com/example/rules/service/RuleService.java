package com.example.rules.service;

import com.example.rules.controller.model.Rule;
import com.example.rules.cypher.CypherRunner;
import com.example.rules.cypher.CypherStatementBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RuleService {
    private final CypherRunner cypherRunner;
    private final ObjectMapper objectMapper;
    @Value("classpath:static/rule-schema.json")
    private Resource ruleSchemaRes;
    private JsonSchema ruleSchema;

    @PostConstruct
    public void postConstruct() {
        try {
            var factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            ruleSchema = factory.getSchema(ruleSchemaRes.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load rule json schema");
        }
    }

    public Set<ValidationMessage> validate(String json) {
        try {
            var jsonNode = objectMapper.readTree(json);
            return ruleSchema.validate(jsonNode);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is not a valid json string");
        }
    }

    public void runRule(String json) {
        var validationMessages = validate(json);
        if (!validationMessages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rule");
        }
        try {
            var rule = objectMapper.readValue(json, Rule.class);
            cypherRunner.run(new CypherStatementBuilder(rule).build());
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rule JSON cannot be converted, but validation is successful");
        }


//        var query = CypherQuery.builder(rule).build();
//        cypherRunner.run(query);
    }
}
