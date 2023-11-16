package com.example.carwash.tasks;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class RemovingRejectedAppointments {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(RemovingRejectedAppointments.class);

    public RemovingRejectedAppointments(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }


    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 12)
    @Transactional
    public void performTask() {
        logger.info("Starting removing rejected appointments.");
        List<Appointment> rejected = appointmentRepository.findAllByStatus(-1);
        rejected.forEach(appointment -> appointment.getUser().getAppointments().remove(appointment));
        logger.info("Found {} rejected appointments.", rejected.size());
        appointmentRepository.deleteAll(rejected);
        logger.info("Finished removing rejected appointments.");
    }
}
