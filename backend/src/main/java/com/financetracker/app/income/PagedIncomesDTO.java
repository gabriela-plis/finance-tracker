package com.financetracker.app.income;

import java.util.List;

public record PagedIncomesDTO(

    int totalPages,

    int currentPage,

    List<IncomeDTO> incomes

) { }
