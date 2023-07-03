package com.zaprogramujzycie.finanse.expense;

import java.util.List;

public record PagedExpensesDTO(

    int totalPages,

    int currentPage,

    List<ExpenseDTO> expenses

) { }
