package com.financetracker.app.report.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
}
