package com.example.rules.cypher.query.match.impl;

import com.example.rules.cypher.query.match.AdditionalMatch;

public class CountryMatch implements AdditionalMatch {
    private final String portPropertyName;
    private final String countryPropertyName;

    public CountryMatch(String portPropertyName) {
        this.portPropertyName = portPropertyName;
        this.countryPropertyName = portPropertyName + "Country";
    }

    @Override
    public String getMatchStatement() {
        return String.format("MATCH (%s)-[:IN]->(%s:Country)", portPropertyName, countryPropertyName);
    }

    @Override
    public String getMatchedPropertyName() {
        return countryPropertyName;
    }
}
