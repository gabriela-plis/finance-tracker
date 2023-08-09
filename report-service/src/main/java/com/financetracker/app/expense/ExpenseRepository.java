package com.financetracker.app.expense;

import com.financetracker.app.expense.ExpenseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<ExpenseEntity, String> {


}
