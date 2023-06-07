package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.expense.Expense;
import com.zaprogramujzycie.finanse.income.Income;
import com.zaprogramujzycie.finanse.security.authorization.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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

    private List<Expense> expenses = new ArrayList<>();

    private List<Income> incomes = new ArrayList<>();

    private List<Role> roles = new ArrayList<>();
}
