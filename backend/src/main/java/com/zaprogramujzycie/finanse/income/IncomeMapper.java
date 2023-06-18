package com.zaprogramujzycie.finanse.income;

import com.zaprogramujzycie.finanse.category.CategoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR, uses = CategoryMapper.class)
public interface IncomeMapper {

    @Mapping(target = "user", ignore = true)
    Income toEntity(IncomeDTO income);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    Income toEntity(AddIncomeDTO income);

    IncomeDTO toDTO(Income income);

    List<IncomeDTO> toDTOs(List<Income> income);
}
