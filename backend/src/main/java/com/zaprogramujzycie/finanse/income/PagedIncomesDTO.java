package com.zaprogramujzycie.finanse.income;

import java.util.List;

public record PagedIncomesDTO(

    int totalPages,

    int currentPage,

    List<IncomeDTO> expenses

) { }
