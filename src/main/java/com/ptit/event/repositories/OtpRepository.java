package com.ptit.event.repositories;

import com.ptit.event.entities.models.Otp;
import com.ptit.event.entities.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUser(User user);
}
