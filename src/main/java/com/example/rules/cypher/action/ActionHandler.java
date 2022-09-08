package com.example.rules.cypher.action;

import com.example.rules.cypher.query.CypherQueryWhere;
import com.example.rules.cypher.query.match.AdditionalMatch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@AllArgsConstructor
public class ActionHandler {
    private final String actionCypher;
    private final Set<AdditionalMatch> additionalMatches;
    @Setter(AccessLevel.PROTECTED)
    private CypherQueryWhere where;

    public ActionHandler(String actionCypher) {
        this.actionCypher = actionCypher;
        this.additionalMatches = new HashSet<>();
    }

    protected void addAdditionalMatch(AdditionalMatch match) {
        this.additionalMatches.add(match);
    }

    public Optional<CypherQueryWhere> getWhere() {
        return Optional.ofNullable(where);
    }

}
