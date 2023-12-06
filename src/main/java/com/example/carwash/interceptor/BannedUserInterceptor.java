package com.example.carwash.interceptor;

import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;
import java.util.Optional;

@Component
public class BannedUserInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(BannedUserInterceptor.class);
    @Autowired
    public BannedUserInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            String name = userPrincipal.getName();

            if (name != null) {
                Optional<User> user = userRepository.findByUsername(name);
                if (user.isPresent() && user.get().isBanned()) {
                    logger.info("Banned user tried to access the system! Username: {}", name);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
            }
        }

        return true;
    }
}
