package com.financetracker.app.user;

import com.financetracker.app.utils.exception.custom.DocumentNotFoundException;
import com.financetracker.app.security.authentication.RegisterDetailsDTO;
import com.financetracker.app.security.authorization.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterDetailsDTO user) {
        User userEntity = userMapper.toEntity(user);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.getRoles().add(Role.USER);

        userRepository.insert(userEntity);

    }

    public boolean userIsExist(String email) {
        return userRepository.findByEmail(email)
            .isPresent();
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);

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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(DocumentNotFoundException::new);
    }
}
