package com.trashzilla.backend.controller;

import com.trashzilla.backend.dto.Admin;
import com.trashzilla.backend.entity.User;
import com.trashzilla.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository repository;

    public AdminController(UserRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500"})
    @GetMapping("/users-info")
    public ResponseEntity<List<Admin>> getUsersBasicInfo() {
        List<User> users = repository.findAll();
        List<Admin> userInfoList = new ArrayList<>();

        for (User user : users) {
            userInfoList.add(new Admin(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getCity() != null ? user.getCity().getName() : null
            ));
        }

        return ResponseEntity.ok(userInfoList);
    }
}


