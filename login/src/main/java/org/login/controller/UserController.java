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

import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;

import java.util.Optional;

import static org.login.Constants.CommonConstants.*;

@RestController
@CrossOrigin
@RequestMapping(AUTH)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(REGISTER)
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(EXIST_USER_ERROR);
            }
            long newUserId = userRepository.findAll().stream()
                    .mapToInt(a -> Math.toIntExact(a.getId()))
                    .max()
                    .orElse(0) + 1;
            user.setId(newUserId);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(USER_CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ERROR_CREATE_USER);
        }
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
                String token = jwtUtil.generateToken(existingUser.get().getUsername());
                return ResponseEntity.ok(new TokenResponse(token));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(INVALID_CREDENTIALS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ERROR_MESSAGE);
        }
    }
}
