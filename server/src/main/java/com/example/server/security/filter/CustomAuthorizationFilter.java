package com.example.server.security.filter;

import com.example.server.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;// = new JwtProvider();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            filterChain.doFilter(request, response);

        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/api/user/send-password-reset")){
            filterChain.doFilter(request, response);
        }else {
            jwtProvider.validateToken(response, request, filterChain);
        }
    }
}
