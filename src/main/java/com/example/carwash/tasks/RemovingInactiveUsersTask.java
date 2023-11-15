package com.example.carwash.tasks;

import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RemovingInactiveUsersTask {

    private final Logger logger = LoggerFactory.getLogger(RemovingInactiveUsersTask.class);
    private final UserRepository userRepository;

    @Autowired
    public RemovingInactiveUsersTask(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(timeUnit = TimeUnit.HOURS,fixedRate = 15)
    private void performTask() {
        logger.info("Starting removing inactive users task.");

        Optional<List<User>> inActiveUsers = userRepository.findByActive(false);
        int inactiveUsersCount = inActiveUsers.map(List::size).orElse(0);
        if (inactiveUsersCount == 0) {
            logger.info("No inactive users found.");
            return;
        }

        logger.info("Found {} inactive users.", inactiveUsersCount);

        inActiveUsers.ifPresent(users -> users.forEach(user -> {
                    logger.info("Removed {} because was inactive.", user.getUsername());
                    userRepository.delete(user);
                }
        ));
    }
}
