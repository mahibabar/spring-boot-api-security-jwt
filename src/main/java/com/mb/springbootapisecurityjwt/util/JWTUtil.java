package com.mb.springbootapisecurityjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JWTUtil {
    private static final String SECRET = "secret";

    public static String generateToken(UserDetails userDetails, Map<String, Object> claims) {
        if (claims == null) {
            claims = new HashMap<>();
        }
        claims.put("userName", userDetails.getUsername());

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static Boolean isValidToken(String token, UserDetails userDetails) {
        return extractUserNameFromToken(token).equals(userDetails.getUsername()) && !isExpired(token);
    }

    public static Boolean isExpired(String token) {
        Date date = extractExpiration(token);
        return date.before(new Date(System.currentTimeMillis()));
    }

    public static String extractUserNameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}
