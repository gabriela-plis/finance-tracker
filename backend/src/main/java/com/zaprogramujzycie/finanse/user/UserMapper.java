package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.security.authentication.RegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;


import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ERROR,
        imports = ArrayList.class) //to fix
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "expenses", expression = "java(new ArrayList<>())")
    @Mapping(target = "incomes", expression = "java(new ArrayList<>())")
    User toEntity(UserDTO user);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expenses", expression = "java(new ArrayList<>())")
    @Mapping(target = "incomes", expression = "java(new ArrayList<>())")
    User toEntity(RegisterRequestDTO user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    @Mapping(target = "incomes", ignore = true)
    void updateEntity(@MappingTarget User userToUpdate, UserDTO updatedUser);

    List<UserDTO> toDTOs(List<User> users);

}
