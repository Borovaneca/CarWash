package com.example.carwash.config;

import com.example.carwash.model.enums.RoleName;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.LoginDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfiguration {

    private final String rememberKey;
    public SecurityConfiguration(@Value("${spring.remember.key}") String rememberKey) {
        this.rememberKey = rememberKey;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/users/forgot-password", "/users/logout", "/users/register", "/users/login", "/users/register", "/about", "/users/login-error").permitAll()
                        .requestMatchers("/api/**", "/contact").permitAll()
                        .requestMatchers("/users/reset-password/**").permitAll()
                        .requestMatchers("/employee/**").hasRole(RoleName.EMPLOYEE.name())
                        .requestMatchers("/manager/**").hasRole(RoleName.MANAGER.name())
                        .requestMatchers("/owner/**").hasRole(RoleName.OWNER.name())
                        .anyRequest().authenticated()

        ).formLogin(
                formLogin -> formLogin
                        .loginPage("/users/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/users/login-error")
        ).logout(logout -> logout.logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
        ).rememberMe(
                rememberMe ->
                        rememberMe
                                .key(rememberKey)
                                .rememberMeParameter("rememberme")
                                .rememberMeCookieName("rememberme")
                                .tokenValiditySeconds(2628000)
                ).build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new LoginDetailsServiceImpl(userRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
