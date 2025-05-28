package com.trashzilla.backend.repository;

import com.trashzilla.backend.entity.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteTypeRepository extends JpaRepository<WasteType, Long> {
}