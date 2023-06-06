package com.zaprogramujzycie.finanse.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collation = "categories")
public class Category {

    @Id
    private String id;

    private String name;
}
