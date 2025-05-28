package com.trashzilla.backend.controller;

import com.trashzilla.backend.entity.WasteType;
import com.trashzilla.backend.repository.WasteTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/waste_types")     // see if change needed for the route !!!!!!!!
public class WasteTypeController {
    private final WasteTypeRepository repository;

    public WasteTypeController(WasteTypeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<WasteType> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public WasteType create(@RequestBody WasteType wasteType) {
        return repository.save(wasteType);
    }

    // -------SEE IF WE CAN DO BETTER THAN CHANGING ALL PROP---------
    @PutMapping("/{id}")
    public ResponseEntity<WasteType> update(@PathVariable("id") Long id, @RequestBody WasteType updatedWasteType) {
        Optional<WasteType> optionalWasteType = repository.findById(id);

        if (optionalWasteType.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WasteType existingWasteType = optionalWasteType.get();
        existingWasteType.setValue(updatedWasteType.getValue());
        existingWasteType.setLabel(updatedWasteType.getLabel());
        existingWasteType.setClassName(updatedWasteType.getClassName());

        WasteType savedWasteType = repository.save(existingWasteType);
        return ResponseEntity.ok(savedWasteType);
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