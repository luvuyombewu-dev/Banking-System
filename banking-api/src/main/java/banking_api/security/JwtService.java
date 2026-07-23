package banking_api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {


    private final String secretKey =
            "c2VjdXJlYmFua2luZ3N5c3RlbXNlY3JldGtleTEyMzQ1Njc4OTA=";


    public String generateToken(String email) {


        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + 3600000)
                )
                .signWith(getSignKey())
                .compact();
    }


    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    public boolean isTokenValid(String token) {

        try {

            extractEmail(token);
            return true;

        } catch (Exception e) {

            return false;
        }
    }


    private SecretKey getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}