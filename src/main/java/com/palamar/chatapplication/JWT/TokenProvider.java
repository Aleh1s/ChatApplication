package com.palamar.chatapplication.JWT;

import com.palamar.chatapplication.entity.user.UserEntity;
import com.palamar.chatapplication.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenProvider {

    private final String secretKey = "helloworld";
    private final Long validTime = 360000000L;
    private final Long validTimeForRefreshToken = 720000000L;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public TokenProvider(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public boolean validateToken (String token) {

        return !Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());

    }

    public String generateToken (UserEntity userEntity) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + validTime);

        return Jwts.builder()
                .setSubject(userEntity.getEmail())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(UserEntity user) {
        Date now = new Date();
        Date validPeriod = new Date(now.getTime() + validTimeForRefreshToken);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(validPeriod)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken (HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer_")) {
            return token.substring("Bearer_".length());
        }

        return null;
    }

    public Authentication authentication (String token) {
        String email = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmailByToken (String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}