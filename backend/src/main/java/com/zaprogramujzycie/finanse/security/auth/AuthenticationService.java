package com.zaprogramujzycie.finanse.security.auth;

import com.zaprogramujzycie.finanse.security.JwtService;
import com.zaprogramujzycie.finanse.security.Role;
import com.zaprogramujzycie.finanse.security.UserDetails;
import com.zaprogramujzycie.finanse.user.User;
import com.zaprogramujzycie.finanse.user.UserDTO;
import com.zaprogramujzycie.finanse.user.UserMapper;
import com.zaprogramujzycie.finanse.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;  //userService?
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO loginUser(LoginRequestDTO request) {
        return null;
    }

    public void logoutUser() {

        }

    public AuthenticationResponseDTO registerUser(RegisterRequestDTO request) {
        //create user, save user, generate token and return (user and token)
        //create user => UserAlreadyExistException

        User user = User.builder()
            .email(request.email())
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .build();

        UserDTO registeredUser = userMapper.toDTO(userRepository.save(user));

        String token = jwtService.generateToken(new UserDetails(user.getEmail(), user.getPassword(), List.of(Role.USER)));

        return new AuthenticationResponseDTO(token, registeredUser);
    }

//    User user = userMapper.toEntity(request);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//    UserDTO registeredUser = userMapper.toDTO(userRepository.save(user));
//
//    String token = jwtService.generateToken(new UserDetails(user.getEmail(), user.getPassword(), List.of(Role.USER)));
//
//        return new AuthenticationResponseDTO(token, registeredUser);

}
