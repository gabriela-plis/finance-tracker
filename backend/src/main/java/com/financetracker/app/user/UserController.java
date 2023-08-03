package com.financetracker.app.user;

import com.financetracker.app.utils.exception.custom.IdNotMatchException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public PagedUsersDTO getUsers(Pageable pageable) {
        return userMapper.toPagedDTO(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userMapper.toDTO(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody @Valid UserDTO user) {
        if (!user.id().equals(id)) {
            throw new IdNotMatchException();
        }
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

}
