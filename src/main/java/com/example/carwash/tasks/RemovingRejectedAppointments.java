package com.example.carwash.tasks;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RemovingRejectedAppointments {

    private final AppointmentRepository appointmentRepository;

    public RemovingRejectedAppointments(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 12)
    @Transactional
    public void performTask() {
        log.info("Starting removing rejected appointments.");
        List<Appointment> rejected = appointmentRepository.findAllByStatus(-1);
        rejected.forEach(appointment -> appointment.getUser().getAppointments().remove(appointment));
        log.info("Found {} rejected appointments.", rejected.size());
        appointmentRepository.deleteAll(rejected);
        log.info("Finished removing rejected appointments.");
    }
}
