package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.controller.models.AuthResponse;
import com.prueba.TarjetasRegalo.controller.models.AuthenticationRequest;
import com.prueba.TarjetasRegalo.controller.models.RegisterRequest;

public interface AuthService {
    AuthResponse register (RegisterRequest request);
        AuthResponse authenticate (AuthenticationRequest request);

}
