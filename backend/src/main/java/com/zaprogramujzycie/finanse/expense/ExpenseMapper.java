package com.zaprogramujzycie.finanse.expense;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface ExpenseMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "id", ignore = true)
    Expense toEntity(ExpenseDTO expense);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "id", ignore = true)
    Expense toEntity(AddExpenseDTO expense);

    ExpenseDTO toDTO(Expense expense);

    List<ExpenseDTO> toDTOs(List<Expense> expenses);

}