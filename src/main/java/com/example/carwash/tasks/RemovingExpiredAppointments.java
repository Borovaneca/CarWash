package com.example.carwash.tasks;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.repository.AppointmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RemovingExpiredAppointments {

    private final AppointmentRepository appointmentRepository;
    private final Logger logger = LoggerFactory.getLogger(RemovingExpiredAppointments.class);

    @Autowired
    public RemovingExpiredAppointments(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 12)
    public void performTask() {
        logger.info("Starting removing expired appointments.");
        Integer expired = appointmentRepository.findAllByMadeForBefore(LocalDateTime.now()).map(List::size).orElse(0);
        logger.info("Found {} expired appointments.", expired);
        appointmentRepository.deleteAllByExpiredTrue();
        logger.info("Finished removing expired appointments.");
    }
}
