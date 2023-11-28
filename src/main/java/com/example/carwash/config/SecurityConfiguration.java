package com.example.carwash.config;

import com.example.carwash.filter.JwtAuthenticationFilter;
import com.example.carwash.model.enums.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfiguration {

    private final String rememberKey;


    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfiguration(@Value("${spring.remember.key}") String rememberKey, AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.rememberKey = rememberKey;
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/","/confirm-email/token/**", "/users/forgot-password", "/users/logout", "/users/register", "/users/login", "/users/register", "/about", "/users/login-error").permitAll()
                        .requestMatchers("/api/about/","/api/index/","/api/services/", "/contact").permitAll()
                        .requestMatchers("/users/reset-password/**").permitAll()
                        .requestMatchers("/employee/**", "/api/appointments/today").hasRole(RoleName.EMPLOYEE.name())
                        .requestMatchers("/manager/**", "/api/awaiting-approval/").hasRole(RoleName.MANAGER.name())
                        .requestMatchers("/owner/**", "/api/owner/users/**").hasRole(RoleName.OWNER.name())
                        .anyRequest().authenticated()


        )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(
                formLogin -> formLogin
                        .loginPage("/users/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/users/login-error")
        ).logout(logout -> logout.logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "jwt", "rememberme")
                .invalidateHttpSession(true)
        ).rememberMe(
                rememberMe ->
                        rememberMe
                                .key(rememberKey)
                                .rememberMeParameter("rememberme")
                                .rememberMeCookieName("rememberme")
                                .tokenValiditySeconds(604800)
                ).build();
    }
}
