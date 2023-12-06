package com.example.carwash.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalTime;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {

    private static final LocalTime MAINTENANCE_START_TIME = LocalTime.of(14, 0);
    private static final LocalTime MAINTENANCE_END_TIME = LocalTime.of(15, 0);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LocalTime now = LocalTime.now();

        if (now.isAfter(MAINTENANCE_START_TIME) && now.isBefore(MAINTENANCE_END_TIME)) {
            String requestURL = request.getRequestURL().toString();

            if (!requestURL.endsWith("/maintenance")) {
                response.sendRedirect("/maintenance");
                return false;
            }

        }
        return true;
    }
}
