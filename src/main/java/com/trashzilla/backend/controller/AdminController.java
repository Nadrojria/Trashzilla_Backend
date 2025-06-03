package com.trashzilla.backend.controller;

import com.trashzilla.backend.dto.Admin;
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

    @GetMapping("/users-info")
    public ResponseEntity<List<Admin>> getUsersBasicInfo() {
        List<User> users = repository.findAll();
        List<Admin> userInfoList = new ArrayList<>();

        for (User user : users) {
            userInfoList.add(new Admin(
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
            @RequestBody Map<String, Object> updates
    ) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("success", false, "message", "User not found"));
        }

        User existingUser = optionalUser.get();

        if (updates.containsKey("firstName")) {
            existingUser.setFirstName((String) updates.get("firstName"));
        }
        if (updates.containsKey("lastName")) {
            existingUser.setLastName((String) updates.get("lastName"));
        }
        if (updates.containsKey("role")) {
            existingUser.setRole((String) updates.get("role"));
        }
        if (updates.containsKey("city")) {
            String cityName = (String) updates.get("city");
            City city = cityRepository.findByName(cityName);
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


