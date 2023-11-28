package com.example.carwash.service.jwt;

import com.example.carwash.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String JWT_SECRET_KEY = "4b5f490d1a0a2f0ce9b61732a4ee8bbd7c0f1608a0eba0940ffd31c68ba51bb9c2e2842f12477704257df5ff2efd102cf9d4393de4e3021db7ec6ca1c8d5f291";

    @Override
    public String generateToken(User user) {
        return "Bearer-" + generateTokenValue(new HashMap<>(), user.getUsername());
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public String generateTokenFromUsername(String username) {
        return "Bearer-" + generateTokenValue(new HashMap<>(), username);
    }

    private String generateTokenValue(Map<String, Object> claims, String username) {
         return Jwts
                    .builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private  <E> E getClaimFromToken(String token, Function<Claims, E> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
