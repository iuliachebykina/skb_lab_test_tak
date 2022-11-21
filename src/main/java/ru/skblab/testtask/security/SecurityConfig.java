package ru.skblab.testtask.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


// По большей мере подключила Spring Security для шифрования пароля
// Но если смотреть в общем на веб приложения на спринг буте,
// то не вижу его без Spring Security, тк с ним можно с помощью пары аннотацию (@PreAuthorize())
// и строчек кода настроить авторизацию и аутентификацию к определенным методам апи по написанным тобой требованиям
// в любой момент можно получить всю доступную (которую ты можешь переопределить) информацию пользователе из SecurityContextHolder
// можно раздавать пользователям роли и давать доступы к методам именно на роли

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/register")
                .permitAll()
                .antMatchers("/h2-console/**")
                .permitAll();
        http.headers().frameOptions().disable();
        return http.build();
    }

}