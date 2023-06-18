package com.zaprogramujzycie.finanse.income;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import com.zaprogramujzycie.finanse.user.User;
import com.zaprogramujzycie.finanse.category.Category;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Document(collection = "income")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income {
    @Id
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @DBRef
    private Category category;

    @Field(targetType = DECIMAL128)
    private BigDecimal price;

    @DBRef
    private User user;
}
