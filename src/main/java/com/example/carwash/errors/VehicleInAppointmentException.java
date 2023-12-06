package com.example.carwash.errors;

public class VehicleInAppointmentException extends RuntimeException {
    private static final String MESSAGE = "Vehicle cannot be deleted because it is in an appointment!";
    public VehicleInAppointmentException() {
        super(MESSAGE);
    }
}
