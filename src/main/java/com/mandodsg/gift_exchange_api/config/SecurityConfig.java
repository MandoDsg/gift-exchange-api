package com.mandodsg.gift_exchange_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Configura usuarios en memoria con roles específicos.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF para facilitar pruebas (no recomendado en producción).
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/participants/**").authenticated()    // Protege los demás endpoints con autenticación.
                        .requestMatchers("/api/participants").hasRole("ADMIN")      // Solo administradores pueden listar participantes.
                        .anyRequest().permitAll()                                            // Permite acceso libre a otros endpoints (si los hay).
                )
                .httpBasic(withDefaults()); // Usa autenticación básica (usuario y contraseña en el encabezado).
        return http.build();
    }

    // Define un codificador de contraseñas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura un usuario en memoria para pruebas rápidas.
    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        var admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}