package com.attendance.attendance_management.configure;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private  final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityConfigure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(disabled -> disabled.disable());
        httpSecurity.authorizeHttpRequests(authorise -> authorise.anyRequest().authenticated());
        httpSecurity.formLogin(Customizer.withDefaults()); // to enable form login
        httpSecurity.httpBasic(Customizer.withDefaults()); // to restAPi access
        return httpSecurity.build();
    }
     @Bean
    public AuthenticationProvider authenticationProvider()
     {
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setUserDetailsService(userDetailsService);
         provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
         return provider;
     }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("boopathi").password("b@123").roles("USER").build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }
}
