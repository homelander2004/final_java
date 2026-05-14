package com.igorblazhko.booking.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class IgorBlazhkoRequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long startedAt = System.currentTimeMillis();
        log.debug("Incoming request: method={}, path={}", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
        long duration = System.currentTimeMillis() - startedAt;
        log.info("Request processed: method={}, path={}, status={}, durationMs={}",
                request.getMethod(), request.getRequestURI(), response.getStatus(), duration);
    }
}