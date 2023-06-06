package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.expense.Expense;
import com.zaprogramujzycie.finanse.income.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;


    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private List<Expense> expenses;

    private List<Income> incomes;


}
