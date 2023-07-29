package com.financetracker.app.config

import com.financetracker.app.security.authentication.CustomUserDetailsService
import com.financetracker.app.security.authentication.JwtService
import com.financetracker.app.security.config.SecurityConfig
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import(SecurityConfig)
class MvcTestsConfig extends Specification {

    @MockBean
    CustomUserDetailsService customUserDetailsService

    @MockBean
    JwtService jwtService

}
