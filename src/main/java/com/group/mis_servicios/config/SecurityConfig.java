package com.group.mis_servicios.config;

import com.group.mis_servicios.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/categorias/**",
                                "/providers/register",
                                "/customers/register",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/flagged-providers/history/**",
                                "/flagged-providers/count/**"
                        ).permitAll()

                        .requestMatchers(
                                "/shifts/availables/**",
                                "/shifts/reservar/**",
                                "/customers/**",
                                "/favorites-list/**",
                                "/reviews/**",
                                "/flagged-providers/flag",
                                "/shifts/mis-turnos-cliente"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                "/providers/me",
                                "/shifts/**"
                        ).hasAuthority("ROLE_PROVIDER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
