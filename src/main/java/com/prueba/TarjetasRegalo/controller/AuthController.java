package com.prueba.TarjetasRegalo.controller;

import com.prueba.TarjetasRegalo.controller.models.AuthResponse;
import com.prueba.TarjetasRegalo.controller.models.AuthenticationRequest;
import com.prueba.TarjetasRegalo.controller.models.RegisterRequest;
import com.prueba.TarjetasRegalo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return  ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return  ResponseEntity.ok(authService.authenticate(request));
    }
}
