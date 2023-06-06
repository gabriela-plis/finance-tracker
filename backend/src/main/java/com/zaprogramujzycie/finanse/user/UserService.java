package com.zaprogramujzycie.finanse.user;

import com.zaprogramujzycie.finanse.security.authentication.RegisterRequestDTO;
import com.zaprogramujzycie.finanse.utils.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registerUser(RegisterRequestDTO user) {
        //create user => UserAlreadyExistException

        User userEntity = userMapper.toEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        return userMapper.toDTO(userRepository.save(userEntity));
    }

    public UserDTO getUserByEmail(String email) {
        User userEntity = userRepository.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);

        return userMapper.toDTO(userEntity);
    }

}
