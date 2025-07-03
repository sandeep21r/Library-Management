package com.LibraryApi.LibraryManagement.Filters
        ;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
//
//        log.info("Incoming Request: {} {}", request.getMethod(), request.getRequestURI());
//        System.out.println(new BufferedReader(new InputStreamReader(request.getInputStream())));
//        filterChain.doFilter(request, response);
//        log.info("Response Status: {}", response.getStatus());

//        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);

        log.info("Incoming Request: {} {}", request.getMethod(), request.getRequestURI());
//        System.out.println("Request Body: " + cachedRequest.getBodyAsString());

        filterChain.doFilter(request, response);

        log.info("Response Status: {}", response.getStatus());
    }
}
