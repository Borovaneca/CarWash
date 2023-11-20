package com.example.carwash.tasks;

import com.example.carwash.model.entity.User;
import com.example.carwash.repository.UserRepository;
import com.example.carwash.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RemovingInactiveUsersTask {

    private final UserService userService;

    @Autowired
    public RemovingInactiveUsersTask(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(timeUnit = TimeUnit.HOURS,fixedRate = 15)
    private void performTask() {
        log.info("Starting removing inactive users task.");

        Optional<List<User>> inActiveUsers = userService.findInactiveUsersMoreThan7Days();
        int inactiveUsersCount = inActiveUsers.map(List::size).orElse(0);
        if (inactiveUsersCount == 0) {
            log.info("No inactive users found.");
            return;
        }

        log.info("Found {} inactive users.", inactiveUsersCount);

        inActiveUsers.ifPresent(users -> users.forEach(user -> {
                    log.info("Removed {} because was inactive.", user.getUsername());
                    userService.delete(user);
                }
        ));
    }
}
