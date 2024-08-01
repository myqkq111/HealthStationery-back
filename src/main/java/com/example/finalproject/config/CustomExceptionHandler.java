package com.example.finalproject.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        handleException(response, HttpServletResponse.SC_FORBIDDEN, "Access Denied");
    }

    private void handleException(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.getWriter().write(String.format("{\"error\": \"%s\"}", message));
    }
}
