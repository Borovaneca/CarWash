package com.example.carwash.repository;

import com.example.carwash.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String value);

    @Query("SELECT u FROM User u WHERE u.isActive = false and day(now()) - day(u.registeredOn) > 7")
    Optional<List<User>> findInactiveUsersMoreThan7Days();

    List<User> findByAppointmentsStatus(Integer status);
}
