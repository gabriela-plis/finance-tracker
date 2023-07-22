package com.financetracker.app.security.authentication

import com.financetracker.app.security.authorization.Role
import com.financetracker.app.user.User
import com.financetracker.app.user.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

class CustomUserDetailsServiceTest extends Specification {

    UserRepository userRepository = Mock()

    CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(userRepository)

    def"should load user by username"() {
        given:
        String username = "anne@gmail.com"
        CustomUserDetails expected = new CustomUserDetails("anne@gmail.com", "anne123", List.of(Role.USER))

        when:
        CustomUserDetails result = customUserDetailsService.loadUserByUsername(username)

        then:
        1 * userRepository.findByEmail(username) >> Optional.of(getUser())

        and:
        result == expected
    }

    def"should not load user by username and throw UsernameNotFoundException"() {
        given:
        String username = "anne@gmail.com"

        when:
        customUserDetailsService.loadUserByUsername(username)

        then:
        1 * userRepository.findByEmail(username ) >> Optional.empty()

        and:
        thrown(UsernameNotFoundException)
    }


    private User getUser() {
        return new User("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }
}
