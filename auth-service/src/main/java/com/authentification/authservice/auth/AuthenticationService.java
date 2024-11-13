package com.authentification.authservice.auth;

import com.authentification.authservice.config.JwtService;
import com.authentification.authservice.user.User;
import com.authentification.authservice.userRepo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
private final UserRepo userRepo;
private final JwtService jwtService;
private final AuthenticationManager authenticationManager;
private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepo.findByEmail(registerRequest.getEmail());
        if(optionalUser.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }else{
            User user = User.builder()
                    .nom(registerRequest.getNom())
                    .prenom(registerRequest.getPrenom())
                    .email(registerRequest.getEmail())
                    .role(registerRequest.getRole())
                    .motDePasse(passwordEncoder.encode(registerRequest.getPassword()))
                    .build();
            userRepo.save(user);
            var jwt = jwtService.generateToken(user);

            return AuthenticationResponse.builder().token(jwt).build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword())
        );
        var user = userRepo.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();

    }
}
