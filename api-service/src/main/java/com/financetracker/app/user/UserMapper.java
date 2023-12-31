package com.financetracker.app.user;

import com.financetracker.app.security.authentication.RegisterDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO user);


    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterDetailsDTO user);

    @Mapping(target = "password", ignore = true)
    void updateEntity(@MappingTarget User userToUpdate, UserDTO updatedUser);

    UserDTO toDTO(User user);

    @Mapping(target = "totalPages", expression = "java(users.getTotalPages())")
    @Mapping(target = "currentPage", expression = "java(users.getNumber())")
    @Mapping(target = "users", expression = "java(toDTOs(users.getContent()))")
    PagedUsersDTO toPagedDTO(Page<User> users);

    List<UserDTO> toDTOs(List<User> users);

}
