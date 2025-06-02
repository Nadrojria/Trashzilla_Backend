package com.trashzilla.backend.controller;

import com.trashzilla.backend.dto.LogIn;
import com.trashzilla.backend.entity.User;
import com.trashzilla.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/login")
public class LogInController {

    private final UserRepository repository;
    public LogInController(UserRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody LogIn login) {
        Optional<User> user = repository.findByEmailAndPassword(
                login.getEmail(), login.getPassword());

        if (user.isPresent()) {
            User u = user.get();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("firstName", u.getFirstName());
            response.put("role", u.getRole());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("success", false));
        }
    }

}


