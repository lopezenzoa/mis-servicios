package com.group.mis_servicios.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                    .cors(Customizer.withDefaults())
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/register", "/auth/login").permitAll()
                            .requestMatchers("/auth/users").permitAll()
                            .requestMatchers("/customers/**").permitAll()
                            .requestMatchers("/providers", "/providers/").permitAll()
                            .requestMatchers("/providers/**").permitAll()
                            .requestMatchers("/shifts/**").permitAll()
                            .requestMatchers("/auth/logout").permitAll()
                            .requestMatchers("/calls/**").permitAll()
                            .requestMatchers("/facilities/**").permitAll()
                            .requestMatchers("/favorites-lists/**").permitAll()
                            .requestMatchers("/favorites-lists/add-provider").permitAll()
                            .requestMatchers("/flagged-providers/**").permitAll()
                            .requestMatchers("/categorias/**").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .anyRequest().authenticated()
                    );

            return http.build();
        }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    }


