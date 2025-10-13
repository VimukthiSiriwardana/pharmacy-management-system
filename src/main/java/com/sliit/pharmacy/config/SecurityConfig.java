// src/main/java/com/sliit/pharmacy/config/SecurityConfig.java
package com.sliit.pharmacy.config;

import com.sliit.pharmacy.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Public pages (no login required)
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/auth/register", "/auth/login").permitAll()

                        // Admin/Pharmacist/Manager pages (must come BEFORE general /orders/** rule)
                        .requestMatchers(
                                "/dashboard",
                                "/inventory/**",
                                "/medicines/**",
                                "/prescriptions/pending",
                                "/prescriptions/approve/**",
                                "/prescriptions/reject/**",
                                "/orders/pending",
                                "/orders/admin/approve/**",
                                "/orders/admin/mark-paid/**",
                                "/feedback/**",
                                "/profile/**"
                        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_PHARMACIST", "ROLE_MANAGER", "ROLE_CUSTOMER")

                        // Customer-only pages
                        .requestMatchers(
                                "/profile/**",
                                "/store/**",
                                "/prescriptions",
                                "/prescriptions/upload",
                                "/orders/**"
                        ).hasAuthority("ROLE_CUSTOMER")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/default", true)  // Redirect handled in LoginRedirectController
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disabled for simplicity (OK for campus project)

        return http.build();
    }
}
