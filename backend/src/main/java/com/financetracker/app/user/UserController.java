package com.financetracker.app.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Api(value = "User Management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation(value = "View a list of available users")
    @GetMapping("/")
    public PagedUsersDTO getUsers(Pageable pageable) {
        return getPagedUsersDTO(userService.getAll(pageable));
    }

    @ApiOperation(value = "Get a user by Id")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userMapper.toDTO(userService.getUserById(id));
    }

    @ApiOperation(value = "Update a user")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return userMapper.toDTO(userService.updateUser(id, userDTO));
    }

    @ApiOperation(value = "Delete a user")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }

    private PagedUsersDTO getPagedUsersDTO(Page<User> pagedUsers) {
        List<UserDTO> users = userMapper.toDTOs(pagedUsers.getContent());
        return new PagedUsersDTO(pagedUsers.getTotalPages(), pagedUsers.getNumber(), users);
    }
}
