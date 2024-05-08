package com.dezzy.springsecurity.config;

import com.dezzy.springsecurity.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.dezzy.springsecurity.data.entity.Role.ADMIN;
import static com.dezzy.springsecurity.data.entity.Role.MANAGER;
import static com.dezzy.springsecurity.data.entity.base.Permissions.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final JwtFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors(withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                "/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/configuration/ui",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/swagger-ui",
                                "/swagger-ui/**",
                                "/webjars/**"
                        ).permitAll()
                                .requestMatchers("/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                                .requestMatchers(GET, "/api/management").hasAnyAuthority(ADMIN_READ.name(), MANAGEMENT_READ.name())
                                .requestMatchers(POST, "/api/management").hasAnyAuthority(ADMIN_WRITE.name(), MANAGEMENT_WRITE.name())
                                .requestMatchers(PUT, "/api/management").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGEMENT_UPDATE.name())
                                .requestMatchers(DELETE, "/api/management").hasAnyAuthority(ADMIN_DELETE.name(), MANAGEMENT_DELETE.name())

                                .anyRequest().authenticated()

                        )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
