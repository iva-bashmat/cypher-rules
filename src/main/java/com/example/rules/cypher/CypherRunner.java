package com.example.rules.cypher;

import com.example.rules.cypher.query.CypherQuery;
import lombok.AllArgsConstructor;
import org.neo4j.driver.Driver;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CypherRunner {

    private final Driver driver;

    public <T> T run(CypherQuery query) {
        try (var session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run(query.getQuery(), query.getParams());
                return "done";
            });
        }
        return null;
    }
}
