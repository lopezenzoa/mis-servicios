package com.group.mis_servicios.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/register", "/auth/login").permitAll()
                            .requestMatchers("/auth/users").permitAll()
                            .requestMatchers("/customers/**").permitAll()
                            .requestMatchers("/providers/**").permitAll()
                            .requestMatchers("/shifts/**").permitAll()
                            .requestMatchers("/auth/logout").permitAll()
                            .requestMatchers("/categorias/**").permitAll()
                            .requestMatchers("/favorites-lists/**").permitAll()
                            .requestMatchers("/favorites-lists/add-provider").permitAll()
                            .anyRequest().authenticated()
                    );

            return http.build();
        }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    }


