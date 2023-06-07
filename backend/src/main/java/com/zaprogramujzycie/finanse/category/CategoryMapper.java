package com.zaprogramujzycie.finanse.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toEntity(AddCategoryDTO category);

    CategoryDTO toDTO(Category category);

    List<CategoryDTO> toDTOs(List<Category> categories);

}
