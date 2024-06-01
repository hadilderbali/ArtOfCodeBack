package com.artofcode.artofcodebck.auth;

import com.artofcode.artofcodebck.user.EmailConfirmationToken;
import com.artofcode.artofcodebck.user.EmailConfirmationTokenRepository;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailConfirmationService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final EmailConfirmationTokenRepository tokenRepository;

    public void sendConfirmationEmail(String userEmail, int userId) {
        User user = userRepository.findById(userId).orElseThrow(); // Fetch user by ID
        String token = UUID.randomUUID().toString();
        EmailConfirmationToken emailToken = new EmailConfirmationToken();
        emailToken.setToken(token);
        emailToken.setUser(user);
        emailToken.setExpiryDate(LocalDateTime.now().plusDays(1)); // Token expiry time
        tokenRepository.save(emailToken);

        String confirmationLink = "http://localhost:8089/user/api/v1/auth/confirmation/confirm?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Confirm Your Registration");
        message.setText("Please click the link below to confirm your registration:\n" + confirmationLink);
        javaMailSender.send(message);
    }

    public void confirmRegistration(String token) {
        EmailConfirmationToken emailToken = (EmailConfirmationToken) tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (emailToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token expired");
        }

        User user = emailToken.getUser();
        user.setEnabled(true);
        user.setConfirmed(true);
        userRepository.save(user);
        tokenRepository.delete(emailToken);
    }
}

