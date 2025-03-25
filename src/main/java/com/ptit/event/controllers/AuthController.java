package com.ptit.event.controllers;

import com.ptit.event.dtos.AuthDTO;
import com.ptit.event.entities.models.User;
import com.ptit.event.responses.AuthResponse;
import com.ptit.event.responses.UserResponse;
import com.ptit.event.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody AuthDTO authDTO) {
        try {
            User user = authService.register(authDTO);
            UserResponse userResponse = UserResponse.fromUser(user);
            return new ResponseEntity<>(
                    userResponse,
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO){
        try {
            String jwt = authService.login(authDTO);
            return new ResponseEntity<>(
                    AuthResponse.builder()
                            .jwt(jwt)
                            .message("Login successfully")
                            .build(),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    AuthResponse.builder()
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.OK
            );
        }
    }
    @PostMapping("/otp/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String email,
                                       @RequestParam String otp){
        try {
            User user = authService.verifyOtp(email, otp);
            UserResponse userResponse = UserResponse.fromUser(user);
            return ResponseEntity.ok(userResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
