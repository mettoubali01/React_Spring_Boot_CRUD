package com.example.crud_react_js.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtConfig jwtConfig;
    private JwtSecretKey jwtSecretKey;
    public JwtUsernamePasswordAuthenticationFilter(JwtConfig jwtConfig,
                                                   JwtSecretKey jwtSecretKey,
                                                   AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
    }

    //the client send the credentials and we validate them
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authenticate = null;
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), //Principal 1
                    authenticationRequest.getPassword() //Principal 2
            );

            //this is make sure that username exists
            //if exists it will check whether the password is correct
            //if s that case then the request it will be authenticated
            authenticate = authenticationManager.authenticate(authentication);

            return  authenticate;
        } catch (IOException e) {
            //return authenticate;
            throw new RuntimeException(e);
        }
    }

    //this method it will be executed when the previous method id not failed
    //this method is to create token and send it back to the client
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //the sign key has to be strong (mixed )and large
        SecretKey key = jwtSecretKey.getSecretKey();
        String token = Jwts.builder()
                //1 -header
                .setSubject(authResult.getName())//name of the user
                //body. (all this could be configurable)
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(Date.valueOf(LocalDate.now().plusWeeks(2)))//this token expire in two weeks
                //sign token (last part of the token
                .signWith(key)
                .compact();

        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
    }
}
