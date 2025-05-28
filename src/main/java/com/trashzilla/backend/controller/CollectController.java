package com.trashzilla.backend.controller;

import com.trashzilla.backend.entity.Collect;
import com.trashzilla.backend.repository.CollectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collects")     // see if change needed for the route !!!!!!!!
public class CollectController {
    private final CollectRepository repository;

    public CollectController(CollectRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Collect> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Collect create(@RequestBody Collect collect) {
        return repository.save(collect);
    }

    // -------SEE IF WE CAN DO BETTER THAN CHANGING ALL PROP---------
    @PutMapping("/{id}")
    public ResponseEntity<Collect> update(@PathVariable("id") Long id, @RequestBody Collect updatedCollect) {
        Optional<Collect> optionalCollect = repository.findById(id);

        if (optionalCollect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Collect existingCollect = optionalCollect.get();
        existingCollect.setDate(updatedCollect.getDate());
        existingCollect.setCity(updatedCollect.getCity());
        existingCollect.setUser(updatedCollect.getUser());
        existingCollect.setCreatedAt(updatedCollect.getCreatedAt());
        existingCollect.setUpdatedAt(updatedCollect.getUpdatedAt());

        Collect savedCollect = repository.save(existingCollect);
        return ResponseEntity.ok(savedCollect);
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