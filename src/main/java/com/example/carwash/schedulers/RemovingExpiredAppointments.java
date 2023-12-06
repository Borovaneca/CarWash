package com.example.carwash.schedulers;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.service.interfaces.AppointmentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RemovingExpiredAppointments {

    private final AppointmentService appointmentService;

    public RemovingExpiredAppointments(@Qualifier("appointmentServiceProxy") AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 12)
    @Transactional
    public void performTask() {
        log.info("Starting removing rejected and expired appointments.");
        List<Appointment> expired = appointmentService.findByMadeForBeforeNow();
        expired.forEach(appointment -> appointment.getUser().getAppointments().remove(appointment));
        log.info("Found {} expired appointments.", expired.size());
        appointmentService.deleteAll(expired);
        log.info("Finished removing rejected and expired appointments.");

    }
}
