package org.login.controller;

import org.login.config.JwtUtil;
import org.login.dto.TokenResponse;
import org.login.entity.User;
import org.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already taken.";
        }
        if (userRepository.findAll().stream().map(a -> a.getId()).count() == 0) {
            user.setId(1L);
        } else {
            int maxId = userRepository.findAll().stream()
                    .mapToInt(a -> Math.toIntExact(a.getId()))
                    .max()
                    .orElse(0); // In case there are no users, default to 0
            user.setId(maxId + 1L);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(new TokenResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
    }
}
