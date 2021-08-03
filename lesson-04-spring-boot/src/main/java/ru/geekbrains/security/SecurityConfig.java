package ru.geekbrains.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    @Configuration
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/**/*.css", "/**/*.js").permitAll()
                    .antMatchers("/product/**").permitAll()
                    .antMatchers("/register/*").permitAll()
                    .antMatchers("/user/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/product")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access_denied");

        }
    }
}
