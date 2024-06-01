package com.artofcode.artofcodebck.auth;

import com.artofcode.artofcodebck.config.JwtService;
import com.artofcode.artofcodebck.token.Token;
import com.artofcode.artofcodebck.token.TokenRepository;
import com.artofcode.artofcodebck.token.TokenType;
import com.artofcode.artofcodebck.user.EmailConfirmationToken;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownServiceException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final EmailConfirmationService emailConfirmationService;
  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
            .confirmed(false)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    emailConfirmationService.sendConfirmationEmail(savedUser.getEmail(), savedUser.getId());

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .role(user.getRole())
        .build();
  }
  public void confirmRegistration(String token) {
    emailConfirmationService.confirmRegistration(token);
  }

  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
    var user = repository.findByEmail(request.getEmail());
    if(user == null){
      System.out.println("not found");
      return ResponseEntity.badRequest().body(AuthenticationResponse.builder()
              .error("Email not found")
              .build());
  }
    if (!user.isConfirmed()) {
      return ResponseEntity.badRequest().body(AuthenticationResponse.builder()
              .error("Please confirm your email")
              .build());
    }
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      return ResponseEntity.badRequest().body(AuthenticationResponse.builder()
              .error("Passwod incorrect")
              .build());
    }

    // Proceed with authentication only if the account is enabled
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return ResponseEntity.ok(AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .role(user.getRole())
            .build());
  }


  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail);

      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
  public String getEmailByToken(HttpServletRequest request) {
    System.out.println("getting token"+request);
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;
    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);
    return userEmail;
  }
}
