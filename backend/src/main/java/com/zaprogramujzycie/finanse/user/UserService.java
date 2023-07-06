package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.security.authentication.RegisterDetailsDTO;
import com.zaprogramujzycie.finanse.security.authorization.Role;
import com.zaprogramujzycie.finanse.utils.exception.DocumentNotFoundException;
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

    public UserDTO getUserByEmail(String email) {
        User userEntity = userRepository.findByEmail(email)
            .orElseThrow(DocumentNotFoundException::new);

        return userMapper.toDTO(userEntity);
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOs(users);
    }

    public UserDTO getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return userMapper.toDTO(user);
    }

    public User save(User existingUser) {
        userRepository.save(existingUser);
        return existingUser;
    }


    public UserDTO updateUser(String id, UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        userMapper.updateEntity(existingUser, userDTO);
        Optional<User> updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    public void delete(String id) {
        Optional<User> existingUser = userRepository.findById(id);
        userRepository.delete(existingUser);
    }
}
