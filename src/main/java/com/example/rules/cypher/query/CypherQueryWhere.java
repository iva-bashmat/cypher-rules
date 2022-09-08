package com.example.rules.cypher.query;

import com.example.rules.cypher.query.operator.BinaryLogicalOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CypherQueryWhere {
    private BinaryLogicalOperator operator;
    private String where;
}
