package com.financetracker.app.user;

import com.financetracker.app.utils.exception.DocumentNotFoundException;
import com.financetracker.app.security.authentication.RegisterDetailsDTO;
import com.financetracker.app.security.authorization.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDetailsDTO user) {
        //create user => UserAlreadyExistException
        User userEntity = userMapper.toEntity(user);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.getRoles().add(Role.USER);

        userRepository.insert(userEntity);

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(DocumentNotFoundException::new);

    }

    public List<User> findAll() {
        return userRepository.findAll();

    }

    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(DocumentNotFoundException::new);
    }

    public User updateUser(String id, UserDTO userToUpdate) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(DocumentNotFoundException::new);
        userMapper.updateEntity(existingUser, userToUpdate);
        return userRepository.save(existingUser);
    }

    public void delete(String id) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(DocumentNotFoundException::new);
        userRepository.delete(existingUser);
    }
}
