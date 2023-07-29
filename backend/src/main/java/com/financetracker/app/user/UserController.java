package com.financetracker.app.user;

import com.financetracker.app.utils.exception.custom.IdNotMatchException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Api(value = "User Management")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation(value = "View a list of available users")
    @GetMapping
    public PagedUsersDTO getUsers(Pageable pageable) {
        return userMapper.toPagedDTO(userService.getAllUsers(pageable));
    }

    @ApiOperation(value = "Get a user by Id")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userMapper.toDTO(userService.getUserById(id));
    }

    @ApiOperation(value = "Update a user")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody UserDTO user) {
        if (!user.id().equals(id)) {
            throw new IdNotMatchException();
        }
        userService.updateUser(id, user);
    }

    @ApiOperation(value = "Delete a user")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

}
