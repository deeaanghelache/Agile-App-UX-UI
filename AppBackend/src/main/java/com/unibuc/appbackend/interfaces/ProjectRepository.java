package com.unibuc.appbackend.interfaces;

import com.unibuc.appbackend.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
