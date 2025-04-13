package ru.fmd.user_service.user_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.fmd.user_service.user_service.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("jwt.private_key")
    private String SECRET_KEY;
    @Value("jwt.ttl")
    private Long JwtTtl;

    public String generateToken(User user){
        return Jwts.builder()
            .subject(user.getLogin())
            .claim("role", user.getRole().name())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(JwtTtl)))
            .signWith(getSignKey())
            .compact();
    }

    public String extractUsername(String token){
        return ExtractClaim(token, Claims::getSubject);
    }

    private <T> T ExtractClaim(String token, Function<Claims,T> resolver){
        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
