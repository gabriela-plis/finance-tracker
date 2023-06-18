package com.zaprogramujzycie.finanse.income;

import com.zaprogramujzycie.finanse.expense.ExpenseDTO;

import java.util.List;

public record PagedIncomeDTO(

                             int totalPages,

                             int currentPage,

                             List<ExpenseDTO> expenses

) { }
