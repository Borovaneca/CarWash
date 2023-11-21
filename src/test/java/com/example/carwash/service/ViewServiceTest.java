package com.example.carwash.service;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.view.MyAppointmentView;
import com.example.carwash.service.interfaces.AppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class ViewServiceTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ViewServiceImpl viewService;

    @Test
    public void testGetMyAppointments() {
        // Given
        String username = "testUser";

        // Mocking data
        Appointment appointment1 = new Appointment(); // Replace with your Appointment entity
        appointment1.setCreateOn(LocalDateTime.parse("2023-11-20T08:00"));
        appointment1.setMadeFor(LocalDateTime.parse("2023-11-21T10:00"));
        appointment1.setStatus(1);

        Appointment appointment2 = new Appointment(); // Replace with your Appointment entity
        appointment2.setCreateOn(LocalDateTime.parse("2023-11-21T09:00"));
        appointment2.setMadeFor(LocalDateTime.parse("2023-11-22T12:00"));
        appointment2.setStatus(0);

        when(appointmentService.findAllByUserUsername(username)).thenReturn(List.of(appointment1, appointment2));

        // Mocking model mapper
        MyAppointmentView appointmentView1 = new MyAppointmentView();
        appointmentView1.setCreateOn("2023-11-20T08:00");
        appointmentView1.setMadeFor("2023-11-21T10:00");
        appointmentView1.setStatus("1");

        MyAppointmentView appointmentView2 = new MyAppointmentView();
        appointmentView2.setCreateOn("2023-11-21T09:00");
        appointmentView2.setMadeFor("2023-11-22T12:00");
        appointmentView2.setStatus("0");

        when(modelMapper.map(any(Appointment.class), eq(MyAppointmentView.class)))
                .thenReturn(appointmentView1, appointmentView2);

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

        // Verify that appointmentService's findAllByUserUsername method was called once with the correct username
        verify(appointmentService, times(1)).findAllByUserUsername(username);

        // Verify that modelMapper's map method was called twice with the correct Appointment class and MyAppointmentView class
        verify(modelMapper, times(2)).map(any(Appointment.class), eq(MyAppointmentView.class));
    }
}
