package com.example.carwash.repository;

import com.example.carwash.model.entity.SocialMedia;
import com.example.carwash.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
    Optional<SocialMedia> findByTypeAndUser(String socialName, User user);

    void deleteByType(String type);
}
