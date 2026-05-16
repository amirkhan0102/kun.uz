package dastrulash.uz.kun.uz.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    // Token yaratish
    public String generateToken(String username, Integer profileId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("profileId", profileId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Tokendan username olish
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Tokendan profileId olish
    public Integer getProfileId(String token) {
        return getClaims(token).get("profileId", Integer.class);
    }

    // Token muddati o'tganmi
    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Token to'g'rimi
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}