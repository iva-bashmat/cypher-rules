package com.example.rules.cypher.query.match.impl;

import com.example.rules.cypher.query.match.AdditionalMatch;

public class LineMatch implements AdditionalMatch {
    private final String linePropertyName;

    public LineMatch() {
        this.linePropertyName = "line";
    }

    @Override
    public String getMatchStatement() {
        return String.format("UNWIND route.lines as lineName MATCH (%s:Line {name: lineName})", linePropertyName);
    }

    @Override
    public String getMatchedPropertyName() {
        return linePropertyName;
    }
}
