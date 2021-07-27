package ru.geekbrains.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    public void authConfig(
              AuthenticationManagerBuilder _authBuilder
            , PasswordEncoder _passwordEncoder
            , UserAuthService _userAuthService) throws Exception {
        _authBuilder.inMemoryAuthentication()
                .withUser("mem_user")
                .password(_passwordEncoder.encode("password"))
                .roles("ADMIN")
                .and()
                .withUser("mem_guest")
                .password(_passwordEncoder.encode("password"))
                .roles("GUEST");

        _authBuilder.userDetailsService(_userAuthService);

    }
}
