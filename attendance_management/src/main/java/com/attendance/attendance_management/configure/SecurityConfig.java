package com.attendance.attendance_management.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityConfigure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(disabled -> disabled.disable());
        httpSecurity.authorizeHttpRequests(authorise -> authorise.anyRequest().authenticated());
        httpSecurity.formLogin(Customizer.withDefaults()); // to enable form login
        httpSecurity.httpBasic(Customizer.withDefaults()); // to restAPi access
        return httpSecurity.build();
    }
}
