package com.example.carwash.service;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.view.MyAppointmentView;
import com.example.carwash.service.interfaces.AppointmentService;
import com.example.carwash.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class ViewServiceTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ViewServiceImpl viewService;

    @Test
    public void testGetMyAppointments() {

        String username = "testUser";

        Appointment appointment1 = new Appointment(); // Replace with your Appointment entity
        appointment1.setCreateOn(LocalDateTime.parse("2023-11-20T08:00"));
        appointment1.setMadeFor(LocalDateTime.parse("2023-11-21T10:00"));
        appointment1.setStatus(1);

        Appointment appointment2 = new Appointment();
        appointment2.setCreateOn(LocalDateTime.parse("2023-11-21T09:00"));
        appointment2.setMadeFor(LocalDateTime.parse("2023-11-22T12:00"));
        appointment2.setStatus(0);

        MyAppointmentView appointmentView1 = new MyAppointmentView();
        appointmentView1.setCreateOn("20.11.2023/08:00");
        appointmentView1.setMadeFor("21.11.2023/10:00");
        appointmentView1.setStatus("APPROVED");

        MyAppointmentView appointmentView2 = new MyAppointmentView();
        appointmentView2.setCreateOn("21.11.2023/09:00");
        appointmentView2.setMadeFor("22.11.2023/12:00");
        appointmentView2.setStatus("PENDING");

        when(appointmentService.getAppointmentsOfUser(username)).thenReturn(List.of(appointmentView1, appointmentView2));
        // When
        List<MyAppointmentView> result = viewService.getMyAppointments(username);

        // Then
        assertEquals(2, result.size());
        assertEquals("20.11.2023/08:00", result.get(0).getCreateOn());
        assertEquals("21.11.2023/10:00", result.get(0).getMadeFor());
        assertEquals("APPROVED", result.get(0).getStatus());

        assertEquals("21.11.2023/09:00", result.get(1).getCreateOn());
        assertEquals("22.11.2023/12:00", result.get(1).getMadeFor());
        assertEquals("PENDING", result.get(1).getStatus());
    }
}
