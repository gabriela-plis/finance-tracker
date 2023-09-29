package com.financetracker.app.security.authentication;

import com.financetracker.app.user.User;
import com.financetracker.app.user.UserService;
import com.financetracker.app.security.authorization.Role;
import com.financetracker.app.utils.exception.custom.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ResponseCookie loginUser(LoginDetailsDTO request) {
        User user = userService.getUserByEmail(request.email());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Wrong email or password");
        }

        return jwtService.generateTokenCookie(new CustomUserDetails(user.getId(), request.password(), List.of(Role.USER)));

    }

    public void registerUser(RegisterDetailsDTO request) {
        if (userService.userIsExist(request.email())) {
            throw new UserAlreadyExistException();
        }

        userService.registerUser(request);
    }

    public String getUserId(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

}
