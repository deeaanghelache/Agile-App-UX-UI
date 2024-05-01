package com.unibuc.appbackend.interfaces;

import com.unibuc.appbackend.entities.TaskAssigned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TaskAssignedRepository extends JpaRepository<TaskAssigned, UUID> {

    @Query(value = "SELECT task_assigned.user_id, task_assigned.description, task_assigned.status, task_assigned.task_assigned_id,  task_assigned.project_id, task_assigned.sprint_id " +
            "FROM task_assigned JOIN user ON (task_assigned.user_id = user.user_id) " +
            "WHERE task_assigned.user_id = :userId", nativeQuery = true)
    List<TaskAssigned> getAllTasks(@Param("userId") UUID userId);

    @Query(value = "SELECT task_assigned.user_id, task_assigned.description, task_assigned.status, task_assigned.task_assigned_id,  task_assigned.project_id, task_assigned.sprint_id " +
            "FROM task_assigned JOIN project ON (task_assigned.project_id = project.project_id) " +
            "WHERE task_assigned.project_id = :projectId", nativeQuery = true)
    List<TaskAssigned> getAllTasksForProject(@Param("projectId") UUID projectId);
}
