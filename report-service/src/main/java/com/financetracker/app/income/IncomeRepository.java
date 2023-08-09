package com.financetracker.app.income;

import com.financetracker.app.income.IncomeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface IncomeRepository extends MongoRepository<IncomeEntity, String> {


}
