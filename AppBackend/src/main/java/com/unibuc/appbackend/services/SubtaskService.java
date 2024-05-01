package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.Subtask;
import com.unibuc.appbackend.entities.TaskAssigned;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.enums.TaskAssignedStatus;
import com.unibuc.appbackend.exceptions.SubtaskNotFoundException;
import com.unibuc.appbackend.interfaces.SubtaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubtaskService {

    private SubtaskRepository subtaskRepository;
    private UserService userService;
    private TaskAssignedService taskAssignedService;

    public SubtaskService(SubtaskRepository subtaskRepository, UserService userService, TaskAssignedService taskAssignedService) {
        this.subtaskRepository = subtaskRepository;
        this.userService = userService;
        this.taskAssignedService = taskAssignedService;
    }

    public Subtask create(UUID userId, UUID taskId, String description) {
        Subtask subtask = new Subtask();
        User user = userService.getUserById(userId);
        TaskAssigned taskAssigned = taskAssignedService.getTaskById(taskId);

        subtask.setUser(user);
        subtask.setDescription(description);
        subtask.setTaskAssigned(taskAssigned);
        subtask.setStatus(TaskAssignedStatus.TO_DO);

        return subtaskRepository.save(subtask);
    }

    public Subtask updateStatus(UUID subtaskId, String status) {
        Optional<Subtask> subtask = subtaskRepository.findById(subtaskId);
        if(subtask.isPresent()) {
            Subtask updatedSubtask = subtask.get();
            TaskAssignedStatus newStatus = switch (status.toLowerCase()) {
                case "todo" -> TaskAssignedStatus.TO_DO;
                case "inprogress" -> TaskAssignedStatus.IN_PROGRESS;
                case "done" -> TaskAssignedStatus.DONE;
                case "nicetohave" -> TaskAssignedStatus.NICE_TO_HAVE;
                default -> null;
            };

            updatedSubtask.setStatus(newStatus);
            return subtaskRepository.save(updatedSubtask);
        } else {
            throw new SubtaskNotFoundException();
        }
    }

    public void delete(UUID subtaskId) {
        Optional<Subtask> subtask = subtaskRepository.findById(subtaskId);
        if (subtask.isPresent()) {
            subtaskRepository.deleteById(subtaskId);
        } else {
            throw new SubtaskNotFoundException();
        }
    }

    public List<Subtask> getAllSubtasksForGivenUser(UUID userId) {
        return subtaskRepository.getAllSubtasks(userId);
    }
}
