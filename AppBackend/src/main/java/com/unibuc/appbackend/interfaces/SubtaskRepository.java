package com.unibuc.appbackend.interfaces;

import com.unibuc.appbackend.entities.Subtask;
import com.unibuc.appbackend.entities.TaskAssigned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {
    @Query(value = "SELECT subtask.subtask_id, subtask.user_id, subtask.description, subtask.status, subtask.task_assigned_id " +
            "FROM subtask JOIN user ON (subtask.user_id = user.user_id) " +
            "WHERE subtask.user_id = :userId", nativeQuery = true)
    List<Subtask> getAllSubtasks(UUID userId);
}
