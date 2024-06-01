package com.artofcode.artofcodebck.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailConfirmationTokenRepository extends JpaRepository<EmailConfirmationToken, Long> {
    Optional<Object> findByToken(String token);
}