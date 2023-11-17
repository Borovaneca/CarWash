package com.example.carwash.service;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.repository.AppointmentRepository;
import com.example.carwash.repository.ServiceRepository;
import com.example.carwash.service.interfaces.AppointmentService;
import com.example.carwash.service.interfaces.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    private AppointmentService serviceToTest;

    @Mock
    private AppointmentRepository mockAppointmentRepository;

    @Mock
    private UserService mockUserService;

    @Mock
    private ServiceRepository mockServiceRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new AppointmentServiceImpl(new ModelMapper(),
                mockUserService, mockAppointmentRepository, mockServiceRepository);
    }


    @Test
    void test() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setService(null);
        appointment.setStatus(0);
        appointment.setUser(null);
        appointment.setVehicle(null);
        mockAppointmentRepository.save(appointment);

        serviceToTest.approveAppointmentById(1L);

        Assertions.assertEquals(1, mockAppointmentRepository.findById(1L).get().getStatus());
    }
}
