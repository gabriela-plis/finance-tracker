package com.financetracker.app.expense;

import com.financetracker.app.category.CategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, uses = CategoryMapper.class)
public interface ExpenseMapper {

    @Mapping(target = "user", ignore = true)
    Expense toEntity(ExpenseDTO expense);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Expense toEntity(AddExpenseDTO expense);

    ExpenseDTO toDTO(Expense expense);

    @Mapping(target = "totalPages", expression = "java(expenses.getTotalPages())")
    @Mapping(target = "currentPage", expression = "java(expenses.getNumber())")
    @Mapping(target = "expenses", expression = "java(toDTOs(expenses.getContent()))")
    PagedExpensesDTO toPagedDTO(Page<Expense> expenses);

    List<ExpenseDTO> toDTOs(List<Expense> expenses);

}