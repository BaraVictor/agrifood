package com.italy.agrifood.config;

import com.italy.agrifood.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable() // Temporar, pentru testare - dezactivare CSRF
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/welcome", "/error", "/login", "/register").permitAll() // Endpoints publice
                        .requestMatchers("/businesses/view", "/customers/view").hasAnyAuthority("VIEWER", "EDITOR", "ADMIN") // Vizualizare
                        .requestMatchers("/businesses/add", "/businesses/update/**", "/customers/add", "/customers/update/**").hasAnyAuthority("EDITOR", "ADMIN") // Adăugare și actualizare
                        .requestMatchers("/businesses/delete/**", "/customers/delete/**").hasAuthority("ADMIN") // Ștergere doar pentru ADMIN
                        .anyRequest().authenticated() // Toate celelalte requesturi necesită autentificare
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // Pagină de autentificare personalizată
                        .loginProcessingUrl("/perform_login")
                        .permitAll()
                        .defaultSuccessUrl("/welcome", true)
                        .failureUrl("/login?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/welcome?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}

