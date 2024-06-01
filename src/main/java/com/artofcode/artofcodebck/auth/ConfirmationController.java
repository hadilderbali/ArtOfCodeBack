package com.artofcode.artofcodebck.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownServiceException;

@RestController
@RequestMapping("/api/v1/auth/confirmation")
@RequiredArgsConstructor
public class ConfirmationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) throws UnknownServiceException {
        authenticationService.confirmRegistration(token);
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "http://localhost:4200/confirmed").build();

    }
}

