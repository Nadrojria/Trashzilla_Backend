package com.trashzilla.backend.repository;

import com.trashzilla.backend.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectRepository extends JpaRepository<Collect, Long> {
}