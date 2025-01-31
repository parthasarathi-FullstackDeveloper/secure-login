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
    // Inject AES Encryption Utility

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            // Encrypt email using AES
            // Hash password using PasswordEncoder
            String encryptedPassword = passwordEncoder.encode(user.getPassword());

            // Check if user already exists
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Username already taken.");
            }

            // Set the user ID and save to the database
            long newUserId = userRepository.findAll().stream()
                    .mapToInt(a -> Math.toIntExact(a.getId()))
                    .max()
                    .orElse(0) + 1;

            user.setId(newUserId);
            user.setPassword(encryptedPassword);

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during registration!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Decrypt input email (username) to match stored encrypted email

            // Find the user by decrypted email
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

            // Validate user credentials
            if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
                // Generate JWT token if credentials match
                String token = jwtUtil.generateToken(existingUser.get().getUsername());

                // Return token in response
                return ResponseEntity.ok(new TokenResponse(token));
            }

            // If credentials do not match
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred!");
        }
    }
}
