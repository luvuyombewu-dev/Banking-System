package banking_api.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {


    @Value("${jwt.secret}")
    private String secretKey;


    @Value("${jwt.expiration}")
    private long jwtExpiration;



    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + jwtExpiration)
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



    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        try {

            String email = extractEmail(token);

            return email.equals(userDetails.getUsername())
                    && !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }



    private boolean isTokenExpired(String token) {

        Date expiration =
                Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration();

        return expiration.before(new Date());
    }



    private SecretKey getSignKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secretKey);


        return Keys.hmacShaKeyFor(keyBytes);
    }

}