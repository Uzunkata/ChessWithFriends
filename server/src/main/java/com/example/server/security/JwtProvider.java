package com.example.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.server.repository.PasswordResetRepository;
import com.example.server.repository.VerifiedRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${oc.token.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.token.jwtExpiration.access}")
    private long jwtExpirationAccess;

    @Value("${oc.token.jwtExpirationEmail}")
    private long jwtExpirationEmail;

    @Value("${oc.token.jwtExpirationPasswordReset}")
    private int jwtExpirationPasswordReset;

    @Autowired
    private VerifiedRepository verificationRepository;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    public JwtProvider() {
        //algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        //verifier = JWT.require(algorithm).build();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {}", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }

    public String getSubjectJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean hasTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody();

        Date tokenExpirationDate = claims.getExpiration();
        Date todayDate = new Date();

        return tokenExpirationDate.before(todayDate);
    }

    //TODO EXPIRATION DATE
    public String generateEmailVerificationHash() {
        String hash = UUID.randomUUID().toString();
        if (verificationRepository.findByHash(hash) == null && passwordResetRepository.findByHash(hash) == null) {
            System.out.println(hash);
            return hash;
        } else {
            generateEmailVerificationHash();
        }
        return "";
    }

    public String generatePasswordResetToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationPasswordReset * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    public void validateToken(HttpServletResponse response, HttpServletRequest request, FilterChain filterChain) throws IOException, ServletException, ServletException, IOException {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = verifier.verify(token);
                String email = decodedJWT.getSubject();
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);

            } catch (Exception exception) {
                logger.error("Error logging in: {}", exception);
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }


    public Map<String, String> generateTokens(User user, String requestURI) {
        Map<String, String> tokens = new HashMap<>();

        //TODO FIX IT (put a debug point in isVerified() method to see it does not even go there)
//        if(!userDetailsService.isVerified(user.getUsername())){
//            return tokens;
//        }


        String access_token = generateAccessToken(user, requestURI);
        tokens.put("access_token", access_token);
        return tokens;
    }


    public String generateAccessToken(User user, String requestURI) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationAccess))
                .withIssuer(requestURI)
                .sign(algorithm);

        return access_token;
    }

}
