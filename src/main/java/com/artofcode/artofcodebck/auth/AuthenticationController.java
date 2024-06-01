package com.artofcode.artofcodebck.auth;

import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:8089/")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return service.authenticate(request);
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

@PostMapping("/decode-token")
  public User decodeToken(
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {
     String email =  service.getEmailByToken(request);
     return userService.getUserByEmail(email);
}
@PostMapping("/getUserByEmail")
  public User getUserByEmail(
       @RequestBody String email)
        throws IOException {
  return userService.getUserByEmail(email);
}
}
