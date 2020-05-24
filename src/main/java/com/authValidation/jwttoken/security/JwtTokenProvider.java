package com.authValidation.jwttoken.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.authValidation.jwttoken.model.entity.TokenDetails;
import com.authValidation.jwttoken.reposatory.JwtTokenRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private static final String AUTH="auth";
    private static final String AUTHORIZATION="Authorization";
    private String secretKey="secret-key";
    private long validityInMilliseconds = 3600000; // 1h

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String name, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(name);
        claims.put(AUTH,roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token =  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        jwtTokenRepository.save(new TokenDetails(token,java.time.LocalDate.now().toString(),java.time.LocalTime.now().toString(),name));
        return token;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        System.out.println(bearerToken);
        if (bearerToken != null ) {
            return bearerToken;
        }
        return null;
    }

    public boolean validateToken(String token) throws JwtException,IllegalArgumentException{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
    }
    public boolean isTokenPresentInDB(String token) {
        return jwtTokenRepository.findById(token).isPresent();
    }

   @SuppressWarnings("unchecked")
    public List<String> getRoleList(String token) {
        return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(AUTH);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public Authentication getAuthentication(String token) {
    	UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
