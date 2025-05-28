package com.trashzilla.backend.controller;

import com.trashzilla.backend.entity.User;
import com.trashzilla.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")     // see if change needed for the route !!!!!!!!
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return repository.save(user);
    }

    // -------SEE IF WE CAN DO BETTER THAN CHANGING ALL PROP---------
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = optionalUser.get();
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setCreatedAt(updatedUser.getCreatedAt());
        existingUser.setUpdatedAt(updatedUser.getUpdatedAt());

        User savedUser = repository.save(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}