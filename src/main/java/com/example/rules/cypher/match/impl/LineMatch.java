package com.example.rules.cypher.match.impl;

import com.example.rules.cypher.match.AttributeMatch;

import java.util.Optional;

public class LineMatch implements AttributeMatch {
    @Override
    public Optional<String> getAdditionalMatch() {
        return Optional.of("UNWIND route.lines as lineName MATCH (line:Line {name: lineName})");
    }
}
