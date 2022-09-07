package com.example.rules.cypher.match.impl;

import com.example.rules.cypher.match.AttributeMatch;

import java.util.Optional;

public class EmptyMatch implements AttributeMatch {
    @Override
    public Optional<String> getAdditionalMatch() {
        return Optional.empty();
    }
}
