package com.example.carwash.repository;

import com.example.carwash.model.entity.Appointment;
import com.example.carwash.model.view.MyAppointmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<List<Appointment>> findAllByMadeForBefore(LocalDateTime now);

    void deleteAllByStatus(Integer status);

    List<Appointment> findAllByUserUsername(String username);

    List<Appointment> findAllByStatus(Integer number);
}
