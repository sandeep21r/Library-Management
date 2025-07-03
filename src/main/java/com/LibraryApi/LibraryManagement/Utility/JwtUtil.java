package com.LibraryApi.LibraryManagement.Utility;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.jsonwebtoken.SignatureAlgorithm.ES384;


@Component
public class JwtUtil {
    private static final String SECRET = "this-is-a-very-secure-secret-key-which-should-be-long-enough_increaing_is_to_make_it_valid";
    private static final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private final long EXP_TIME =6000000;


    private Key getSigningKey(){
        return  Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username,UUID user_id, UUID tenant_id, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXP_TIME);

        Map<String, Object> claims = new HashMap<>();
        if (tenant_id != null && user_id != null) {
            claims.put("tenant", tenant_id.toString());
            claims.put("user",user_id.toString());
        }
        claims.put("role", role);

        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)  // Use the same SecretKey
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidate(String Token){

        return  !isTokenExpired(Token);
    }
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }


}
