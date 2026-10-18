package dev.akhileshaher.backend.controller;

import dev.akhileshaher.backend.dto.LoginDTO;
import dev.akhileshaher.backend.dto.LoginResponseDTO;
import dev.akhileshaher.backend.dto.RegisterRequestDTO;
import dev.akhileshaher.backend.entity.User;
import dev.akhileshaher.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authService.registerUser(registerRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }


}
