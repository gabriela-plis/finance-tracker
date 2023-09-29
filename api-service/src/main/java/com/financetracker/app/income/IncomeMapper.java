package com.financetracker.app.income;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface IncomeMapper {

    @Mapping(target = "user", ignore = true)
    Income toEntity(IncomeDTO income);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Income toEntity(AddIncomeDTO income);

    IncomeDTO toDTO(Income income);

    @Mapping(target = "totalPages", expression = "java(incomes.getTotalPages())")
    @Mapping(target = "currentPage", expression = "java(incomes.getNumber())")
    @Mapping(target = "incomes", expression = "java(toDTOs(incomes.getContent()))")
    PagedIncomesDTO toPagedDTO(Page<Income> incomes);

    List<IncomeDTO> toDTOs(List<Income> incomes);
}
