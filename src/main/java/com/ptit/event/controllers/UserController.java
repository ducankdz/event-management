package com.ptit.event.controllers;

import com.ptit.event.entities.models.User;
import com.ptit.event.responses.UserResponse;
import com.ptit.event.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String jwt){
        try {
            User user = userService.findUserByJwt(jwt);
            UserResponse userResponse = UserResponse.fromUser(user);
            return ResponseEntity.ok(userResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String jwt,
                                         @PathVariable("id") Long id){
        try {
            User reqUser = userService.findUserByJwt(jwt);
            User user = userService.findUserById(id);
            UserResponse userResponse = UserResponse.fromUser(user);
            return ResponseEntity.ok(userResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
