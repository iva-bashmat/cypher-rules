package com.example.rules.cypher.match.impl;

import com.example.rules.cypher.match.AttributeMatch;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class CountryMatch implements AttributeMatch {
    private final String portVariableCypher;
    private final String countryVariableCypher;

    @Override
    public Optional<String> getAdditionalMatch() {
        return Optional.of(String.format("MATCH (%s)-[:IN]->(%s:Country)", portVariableCypher, countryVariableCypher));
    }
}
