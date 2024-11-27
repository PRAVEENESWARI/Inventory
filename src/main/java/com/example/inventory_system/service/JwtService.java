package com.example.inventory_system.service;

import com.example.inventory_system.model.LoginEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
@Service
public class JwtService {
    private final String secretKey = "c7a291fb85dda4d52f6ca12057a85d3dd0c66177" +
            "1ff485e602d6a82e78f67b202d13c4696c750b44ba086dbe19473092bbd0c05cb9670" +
            "47e714cd406f495ba8d36b97f75e5f9bfd18fbea037b1d5910f2c80dabc5559c00858b22" +
            "b8821e366ddbdd9d776aa133c18cf2aff79809e9cd0bc33b59c33852b488c5fd0c61d00919" +
            "d441479b9dfeefb40eb6f2e2bb0432e1d15ada0d2a923f2281870ecab534197451c24b3a593a86cc" +
            "67dfae1eb17659c295b8da909e6179a423e97d966aff5c045e4ec89e312b138af9b2913e169be2199063d3" +
            "37942f45ca977f79379d6425712be0d39f7beb489de15e35b88315c192b32c3bc7887c1d5270db24ed321106402";

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *1))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token, LoginEntity userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUserName()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
