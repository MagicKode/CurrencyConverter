package com.example.currencyconverter.controller;

import com.example.currencyconverter.config.jwt.JwtProvider;
import com.example.currencyconverter.model.entity.User;
import com.example.currencyconverter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) {
        userService.save(user);
        return new ResponseEntity<>("User was created", HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody User user) {
        User userFromDB = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        return new ResponseEntity<>(jwtProvider.generateToken(userFromDB.getLogin()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/testadm")
    public ResponseEntity<String> testAdmin() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/testusr")
    public ResponseEntity<String> testUser() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
