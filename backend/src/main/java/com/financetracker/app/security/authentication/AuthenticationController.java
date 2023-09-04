package com.financetracker.app.security.authentication;

import com.financetracker.api.mail.MailDTO;
import com.financetracker.api.mail.Template;
import com.financetracker.app.mail.MailService;
import com.financetracker.app.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterDetailsDTO registerDetails) {
        authenticationService.registerUser(registerDetails);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginDetailsDTO loginDetails) {
        ResponseCookie jwtCookie = authenticationService.loginUser(loginDetails);

        MailDTO mail = mailService.createGreetingMail(userService.getUserByEmail(loginDetails.email()), Template.GREETING, "abc");
        mailService.sendMail(mail);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }
}
