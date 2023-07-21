package com.financetracker.app.security.authentication;

import com.financetracker.app.user.User;
import com.financetracker.app.user.UserRepository;
import com.financetracker.app.security.authorization.Role;
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
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new CustomUserDetails(user.getEmail(), user.getPassword(), List.of(Role.USER));
    }
}
