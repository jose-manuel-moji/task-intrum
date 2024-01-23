package com.intrum.task.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intrum.task.data.entities.ProcessFileEntity;

@Repository
public interface ProcessFileRepository extends JpaRepository<ProcessFileEntity, String> {
}