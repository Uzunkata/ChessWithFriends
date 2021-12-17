package com.example.server.security.filter;

import com.example.server.exception.ServiceException;
import com.example.server.repository.UserRepository;
import com.example.server.security.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tsohr.JSONObject;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtProvider jwtProvider; //= new JwtProvider();

    @Autowired
    private ServiceException serviceException;


    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext applicationContext) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = applicationContext.getBean(JwtProvider.class);
        this.userRepository = applicationContext.getBean(UserRepository.class);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = "";
        String password = "";
        try {
            String parsedReq = getJsonAsString(request);
            if (parsedReq != null) {
                JSONObject jsonObject = new JSONObject(parsedReq);
                username = jsonObject.getString("username");
                password = jsonObject.getString("password");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);

    }

    private String getJsonAsString(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        if (!userRepository.findByUsername(user.getUsername()).getVerified()) {
            new ObjectMapper().writeValue(response.getOutputStream(), "Email not confirmed");

        } else {
            Map<String, String> tokens = jwtProvider.generateTokens(user, request.getRequestURI().toString());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        }
    }
}
