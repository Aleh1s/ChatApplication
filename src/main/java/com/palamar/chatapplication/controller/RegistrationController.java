package com.palamar.chatapplication.controller;

import com.palamar.chatapplication.body.request.RegistrationRequest;
import com.palamar.chatapplication.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@CrossOrigin("http://localhost:3000")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
