package com.LibraryApi.LibraryManagement.Filters;

import com.LibraryApi.LibraryManagement.Utility.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try{
                String token = header.substring(7);

                Claims claims = jwtUtil.extractToken(token);
                String username = claims.getSubject();
                String user_id = claims.get("user", String.class);
                String tenant_id = claims.get("tenant", String.class);
                String role = claims.get("role", String.class);

                UUID userId = UUID.fromString(user_id);
                UUID tenantId = UUID.fromString(tenant_id);

                if(SecurityContextHolder.getContext().getAuthentication() == null){

                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,Collections.singletonList(authority));
                    // Add userId and tenantId as a map in details
                    usernamePasswordAuthenticationToken.setDetails(Map.of(
                            "user_id", userId,
                            "tenant_id", tenantId,
                            "role", role
                    ));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }catch (ExpiredJwtException ex){
                request.setAttribute("status", HttpServletResponse.SC_UNAUTHORIZED);
                request.setAttribute("jwt_error", "Token has expire");
            }
            catch (Exception e) {
                request.setAttribute("status", HttpServletResponse.SC_UNAUTHORIZED);
                request.setAttribute("jwt_error", "Missing or Invalid Token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
