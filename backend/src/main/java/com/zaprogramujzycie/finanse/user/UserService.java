package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.security.authentication.RegisterDetailsDTO;
import com.zaprogramujzycie.finanse.security.authorization.Role;
import com.zaprogramujzycie.finanse.utils.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserDTO getUserByEmail(String email) {
        User userEntity = userRepository.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);

        return userMapper.toDTO(userEntity);
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public User save(User existingUser) {
        userRepository.save(existingUser);
        return existingUser;
    }
}
