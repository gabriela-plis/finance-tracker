package com.financetracker.app.income;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    List<IncomeDTO> toDTOs(List<Income> incomes);
}
