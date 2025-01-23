package org.login.controller;

import org.login.entity.User;
import org.login.repository.UserRepository;
import org.login.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

@Autowired
      UserRepository userRepository;
    @Autowired
      PasswordEncoder passwordEncoder;
@Autowired
    CustomUserDetailsService service;

    // Register a new user
    @PostMapping("/register") // Use /register for registration, not /login
    public String registerUser(@RequestBody User user) {
        // Encrypt the user's password before saving it to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.createUser();
        userRepository.save(user);
        return "User registered successfully!";
    }

}
