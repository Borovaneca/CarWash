package com.example.carwash.filter;

import com.example.carwash.service.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private UserDetails userDetails;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/users/login") || request.getRequestURI().equals("/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Cookie jwtCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("jwt")).findFirst().orElse(null);
        String authorizationHeader = jwtCookie != null ? jwtCookie.getValue() : null;
        final String jtw;
        final String username;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer-")) {
            filterChain.doFilter(request, response);
            return;
        }
        jtw = authorizationHeader.substring(7);
        try {
            username = jwtService.getUsernameFromToken(jtw);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jtw, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            boolean rememberme = Arrays.stream(request.getCookies()).anyMatch(cookie -> cookie.getName().equals("rememberme"));
            if (rememberme) {
                String newJwt = jwtService.generateTokenFromUsername(userDetails.getUsername());
                Cookie newJwtCookie = new Cookie("jwt", newJwt);
                newJwtCookie.setMaxAge(60 * 60 * 24);
                newJwtCookie.setPath("/");
                response.addCookie(newJwtCookie);
            }
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
        }
    }
}
