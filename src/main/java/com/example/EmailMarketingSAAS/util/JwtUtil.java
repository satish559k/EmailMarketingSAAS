package com.example.EmailMarketingSAAS.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secretkey}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.trim().getBytes(StandardCharsets.UTF_8));
    }

    public  String generateToken(UserDetails userDetail) {
        try {
            Map<String,Object> claim =new HashMap<>();
            Set<String> roles = userDetail.getAuthorities().stream()
                    .map(auth->auth.getAuthority()) // Get role names
                    .collect(Collectors.toSet());
            claim.put("userName",userDetail.getUsername());
            claim.put("roles",roles);
            return Jwts.builder()
                    .setClaims(claim)
                    .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime()+1000*60*60*24))//24hr expiry
//                    .setExpiration(new Date(new Date().getTime()+60*1000)) // 1 minute expiry
                    .setExpiration(new Date(new Date().getTime()+ 30 * 60 * 1000)) // 30 minute expiry
                    .setSubject(userDetail.getUsername())
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact().trim();
        }catch (SecurityException e) {
            log.error("Security issue while generating JWT", e);
            throw new RuntimeException("Token signing failed");
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument during JWT creation", e);
            throw new RuntimeException("Invalid JWT parameters");
        } catch (JwtException e) {
            log.error("JWT error during token generation", e);
            throw new RuntimeException("JWT creation failed");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Token validation error: "+e);
//            System.out.println("Token validation error: " + e.getMessage());
            return false;
        }
    }

    public  String getUserNameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
                .build().parseClaimsJws(token).getBody().getSubject().trim();
    }

    public  boolean isTokenExpired(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
