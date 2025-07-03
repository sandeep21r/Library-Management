package com.LibraryApi.LibraryManagement.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        String message = "Unauthorized To The Resource";

        Object jwtError = request.getAttribute("jwt_error");
        if (jwtError != null) {
            message = jwtError.toString();
        }
        Object statusAttr = request.getAttribute("status");
        if (statusAttr instanceof Integer) {
            int status = (Integer) statusAttr;
            response.setStatus(status);
        }

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", true);
        errorDetails.put("message", message);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
}
