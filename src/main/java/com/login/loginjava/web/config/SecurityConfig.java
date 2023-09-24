package com.login.loginjava.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN","CUSTOMER") //permiso para todos
                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public UserDetailsService memoryUsers() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin124"))
                .roles("ADMIN")
                .build();
        UserDetails customer = User.builder()
                .username("customer")
                .password(passwordEncoder().encode("customer12"))
                .roles("CUSTOMER")
                .build();
        return new InMemoryUserDetailsManager(admin, customer);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
