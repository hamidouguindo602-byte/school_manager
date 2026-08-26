package com.schoolmanagement.authentication.securite;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class ConfigurationSecurite {

        private final FiltreSecurite filtreSecurite;

        public ConfigurationSecurite(FiltreSecurite filtreSecurite) {
                this.filtreSecurite = filtreSecurite;
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(
                                        "/api/authentification/connexion",
                                        "/api/authentification/mot-de-passe-oublie",
                                        "/api/authentification/reinitialiser-mot-de-passe"

                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        filtreSecurite,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}