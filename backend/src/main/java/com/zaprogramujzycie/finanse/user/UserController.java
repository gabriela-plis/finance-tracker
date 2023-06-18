package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "Add a user")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User newUser = userMapper.toEntity(userDTO);
        User createdUser = userService.save(newUser);
        return userMapper.toDTO(createdUser);
    }

    @ApiOperation(value = "View a list of available users")
    @GetMapping
    public List<UserDTO> getUsers() {
        List<User> users = userService.findAll();
        return userMapper.toDTOs(users);
    }

    @ApiOperation(value = "Get a user by Id")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        User user = userService.getUserById(id);
        return userMapper.toDTO(user);
    }

    @ApiOperation(value = "Update a user")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        User existingUser = userService.getUserById(id);
        userMapper.updateEntity(existingUser, userDTO);
        User updatedUser = userService.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @ApiOperation(value = "Delete a user")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
