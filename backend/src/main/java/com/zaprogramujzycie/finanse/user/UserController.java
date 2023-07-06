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

    @ApiOperation(value = "View a list of available users")
    @GetMapping("/")
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @ApiOperation(value = "Get a user by Id")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @ApiOperation(value = "Update a user")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @ApiOperation(value = "Delete a user")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.delete(id);
    }
}
