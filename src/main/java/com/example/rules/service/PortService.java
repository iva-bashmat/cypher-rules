package com.example.rules.service;

import lombok.AllArgsConstructor;
import org.neo4j.driver.Driver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PortService {
    private final Driver driver;

    public List<String> getPorts() {
        try (var session = driver.session()) {
            return session.run("MATCH (p:Port) RETURN p.code AS code ORDER BY code ASC")
                    .list(r -> r.get("code").asString());
        }
    }
}

