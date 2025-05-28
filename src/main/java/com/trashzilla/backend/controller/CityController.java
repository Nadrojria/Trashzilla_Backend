package com.trashzilla.backend.controller;

import com.trashzilla.backend.entity.City;
import com.trashzilla.backend.repository.CityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")     // see if change needed for the route !!!!!!!!
public class CityController {
    private final CityRepository repository;

    public CityController(CityRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<City> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public City create(@RequestBody City city) {
        return repository.save(city);
    }

    // -------SEE IF WE CAN DO BETTER THAN CHANGING ALL PROP---------
    @PutMapping("/{id}")
    public ResponseEntity<City> update(@PathVariable("id") Long id, @RequestBody City updatedCity) {
        Optional<City> optionalCity = repository.findById(id);

        if (optionalCity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        City existingCity = optionalCity.get();
        existingCity.setName(updatedCity.getName());
        existingCity.setLat(updatedCity.getLat());
        existingCity.setLng(updatedCity.getLng());
        existingCity.setCreatedAt(updatedCity.getCreatedAt());
        existingCity.setUpdatedAt(updatedCity.getUpdatedAt());

        City savedCity = repository.save(existingCity);
        return ResponseEntity.ok(savedCity);
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