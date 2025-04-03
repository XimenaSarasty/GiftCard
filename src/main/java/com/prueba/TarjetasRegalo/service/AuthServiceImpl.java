package com.prueba.TarjetasRegalo.service;

import com.prueba.TarjetasRegalo.controller.models.AuthResponse;
import com.prueba.TarjetasRegalo.controller.models.AuthenticationRequest;
import com.prueba.TarjetasRegalo.controller.models.RegisterRequest;
import com.prueba.TarjetasRegalo.entity.Role;
import com.prueba.TarjetasRegalo.entity.User;
import com.prueba.TarjetasRegalo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .nameUser(request.getNameUser())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNameUser(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByNameUser(request.getNameUser()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}
