package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.security.authentication.RegisterDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;
import java.util.Optional;

import static org.mapstruct.ReportingPolicy.ERROR;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ERROR)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO user);


    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(RegisterDetailsDTO user);

    @Mapping(target = "password", ignore = true)
    void updateEntity(@MappingTarget Optional<User> userToUpdate, UserDTO updatedUser);

    UserDTO toDTO(Optional<User> user);

    List<UserDTO> toDTOs(List<User> users);

}
