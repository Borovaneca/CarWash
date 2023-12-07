package com.example.carwash.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalTime;

@Component
@Slf4j
public class MaintenanceInterceptor implements HandlerInterceptor {

    private static final LocalTime MAINTENANCE_START_TIME = LocalTime.of(15, 0);
    private static final LocalTime MAINTENANCE_END_TIME = LocalTime.of(16, 0);

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        LocalTime now = LocalTime.now();

        if (now.isAfter(MAINTENANCE_START_TIME) && now.isBefore(MAINTENANCE_END_TIME)) {
            String requestURL = request.getRequestURL().toString();

            if (!requestURL.endsWith("/maintenance")) {
                log.info("Maintenance mode is active! Redirecting to maintenance page!");
                response.sendRedirect("/maintenance");
                return false;
            }

        }
        return true;
    }
}
