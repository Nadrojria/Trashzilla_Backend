package com.trashzilla.backend.controller;

import com.trashzilla.backend.DTO.UserIdentityDTO;
import com.trashzilla.backend.DTO.UserDTO;
import com.trashzilla.backend.entity.City;
import com.trashzilla.backend.entity.User;
import com.trashzilla.backend.repository.CityRepository;
import com.trashzilla.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://127.0.0.1:5500"})
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository repository;
    private final CityRepository cityRepository;

    public AdminController(UserRepository repository, CityRepository cityRepository) {
        this.repository = repository;
        this.cityRepository = cityRepository;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserIdentityDTO>> getUsersBasicInfo() {
        List<User> users = repository.findAll();
        List<UserIdentityDTO> userInfoList = new ArrayList<>();

        for (User user : users) {
            userInfoList.add(new UserIdentityDTO(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getCity() != null ? user.getCity().getName() : null
            ));
        }

        return ResponseEntity.ok(userInfoList);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserDTO updatedUser) {
        Optional<User> optionalUser = repository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = optionalUser.get();

        if (updatedUser.getFirstName() != null && !updatedUser.getFirstName().isBlank()) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }

        if (updatedUser.getLastName() != null && !updatedUser.getLastName().isBlank()) {
            existingUser.setLastName(updatedUser.getLastName());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        if (updatedUser.getRole() != null && !updatedUser.getRole().isBlank()) {
            existingUser.setRole(updatedUser.getRole());
        }

        if (updatedUser.getCity() != null && !updatedUser.getCity().isBlank()) {
            City city = cityRepository.findByName(updatedUser.getCity());
            if (city == null) {
                return ResponseEntity.badRequest().body(Map.of("success", false, "message", "City not found"));
            }
            existingUser.setCity(city);
        }

        repository.save(existingUser);
        return ResponseEntity.ok(Map.of("success", true));
    }



    @DeleteMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(401).body(Map.of("success", false));
        }

        repository.deleteById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }
}


