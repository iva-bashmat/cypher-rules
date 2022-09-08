package com.example.rules.cypher.expression;

import com.example.rules.cypher.query.match.AdditionalMatch;
import lombok.Data;

import java.util.Optional;

@Data
public class CypherAttribute {
    private String cypherAttributeName;
    private AdditionalMatch additionalMatch;

    public CypherAttribute(String cypherAttributeName) {
        this.cypherAttributeName = cypherAttributeName;
        this.additionalMatch = null;
    }

    public CypherAttribute(String cypherAttributeName, AdditionalMatch additionalMatch) {
        this.cypherAttributeName = cypherAttributeName;
        this.additionalMatch = additionalMatch;
    }

    public Optional<AdditionalMatch> getAdditionalMatch() {
        return Optional.ofNullable(additionalMatch);
    }

}
