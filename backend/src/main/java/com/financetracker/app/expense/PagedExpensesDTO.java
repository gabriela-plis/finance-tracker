package com.financetracker.app.expense;

import java.util.List;

public record PagedExpensesDTO(

    int totalPages,

    int currentPage,

    List<ExpenseDTO> expenses

) { }
