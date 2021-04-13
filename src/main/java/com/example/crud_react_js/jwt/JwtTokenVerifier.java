package com.example.crud_react_js.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private JwtConfig jwtConfig;
    private JwtSecretKey jwtSecretKey;

    public JwtTokenVerifier(JwtConfig jwtConfig, JwtSecretKey jwtSecretKey) {
        this.jwtConfig = jwtConfig;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        String token = requestHeader.replace(jwtConfig.getTokenPrefix(), "");

        if (Strings.isNullOrEmpty(requestHeader) || !requestHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey.getSecretKey())
                    .build().parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //pass to the next filter til get to the Api
            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token $s it cannot be trust!", token));
        }
    }
}
