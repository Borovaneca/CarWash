package com.example.carwash.repository;

import com.example.carwash.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<List<Appointment>> findAllByMadeForBefore(LocalDateTime now);

    @Query("select a from Appointment a where day(a.madeFor) = day(now())")
    List<Appointment> findAllAppointmentsForToday();
    void deleteAllByStatus(Integer status);

    List<Appointment> findAllByUserUsername(String username);

    List<Appointment> findAllByStatus(Integer number);

    List<Appointment> findByMadeForBefore(LocalDateTime now);
}
