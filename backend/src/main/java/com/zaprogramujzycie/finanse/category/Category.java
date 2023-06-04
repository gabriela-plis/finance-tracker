package com.zaprogramujzycie.finanse.category;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "categories")
public class Category {

    @Id
    private String id;

    private String name;
}
