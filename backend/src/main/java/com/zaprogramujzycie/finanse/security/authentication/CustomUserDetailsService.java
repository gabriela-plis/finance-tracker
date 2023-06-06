package com.zaprogramujzycie.finanse.security.authentication;

import com.zaprogramujzycie.finanse.security.authorization.Role;
import com.zaprogramujzycie.finanse.user.User;
import com.zaprogramujzycie.finanse.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new UserDetails(user.getEmail(), user.getPassword(), List.of(Role.USER));
    }
}
