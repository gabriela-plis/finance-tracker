package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.security.auth.RegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface UserMapper {

    UserDTO toDTO(User user);

//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "expenses", expression = "java(new ArrayList<Expense>())")
//    @Mapping(target = "incomes", expression = "java(new ArrayList<Income>())")
//    User toEntity(UserDTO user);
//
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "expenses", expression = "java(new ArrayList<Expense>())")
//    @Mapping(target = "incomes", expression = "java(new ArrayList<Income>())")
//    User toEntity(RegisterRequestDTO user);
//
//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "expenses", ignore = true)
//    @Mapping(target = "incomes", ignore = true)
//    void updateEntity(@MappingTarget User userToUpdate, UserDTO updatedUser);
}
