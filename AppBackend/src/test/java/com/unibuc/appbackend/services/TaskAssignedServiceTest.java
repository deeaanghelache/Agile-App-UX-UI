package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.entities.Sprint;
import com.unibuc.appbackend.entities.TaskAssigned;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.enums.TaskAssignedStatus;
import com.unibuc.appbackend.exceptions.TaskAssignedNotFoundException;
import com.unibuc.appbackend.interfaces.TaskAssignedRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskAssignedServiceTest {

    @InjectMocks
    private TaskAssignedService taskAssignedService;

    @Mock
    private TaskAssignedRepository taskAssignedRepository;

    @Mock
    private UserService userService;

    @Mock
    private SprintService sprintService;

    @Mock
    private ProjectService projectService;

    @Test
    void createTaskAssigned_shouldReturnCreatedTaskAssigned() {
        UUID userId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        UUID sprintId = UUID.randomUUID();
        String description = "Task Description";

        User user = new User(userId, "John", "Doe", "john.doe@example.com", "password");
        Project project = new Project(projectId, "Project Name", "Project Description");
        Sprint sprint = new Sprint(sprintId, LocalDate.now());

        TaskAssigned expectedTaskAssigned = new TaskAssigned();
        expectedTaskAssigned.setUser(user);
        expectedTaskAssigned.setDescription(description);
        expectedTaskAssigned.setProject(project);
        expectedTaskAssigned.setSprint(sprint);
        expectedTaskAssigned.setStatus(TaskAssignedStatus.TO_DO);

        when(userService.getUserById(userId)).thenReturn(user);
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(sprintService.getSprintById(sprintId)).thenReturn(sprint);
        when(taskAssignedRepository.save(any(TaskAssigned.class))).thenReturn(expectedTaskAssigned);

        TaskAssigned result = taskAssignedService.create(userId, projectId, sprintId, description);

        assertNotNull(result);
        assertEquals(expectedTaskAssigned.getUser(), result.getUser());
        assertEquals(expectedTaskAssigned.getDescription(), result.getDescription());
        assertEquals(expectedTaskAssigned.getProject(), result.getProject());
        assertEquals(expectedTaskAssigned.getSprint(), result.getSprint());
        assertEquals(expectedTaskAssigned.getStatus(), result.getStatus());

        verify(userService).getUserById(userId);
        verify(projectService).getProjectById(projectId);
        verify(sprintService).getSprintById(sprintId);
        verify(taskAssignedRepository).save(any(TaskAssigned.class));
    }

    @Test
    void getTaskById_existingTaskAssigned_shouldReturnTaskAssigned() {
        UUID taskId = UUID.randomUUID();
        TaskAssigned expectedTaskAssigned = new TaskAssigned(taskId, "Task Description", TaskAssignedStatus.TO_DO);

        when(taskAssignedRepository.findById(taskId)).thenReturn(Optional.of(expectedTaskAssigned));

        TaskAssigned result = taskAssignedService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(expectedTaskAssigned.getTaskAssignedId(), result.getTaskAssignedId());
        assertEquals(expectedTaskAssigned.getDescription(), result.getDescription());
        assertEquals(expectedTaskAssigned.getStatus(), result.getStatus());

        verify(taskAssignedRepository).findById(taskId);
    }

    @Test
    void getTaskById_nonexistentTaskAssigned_shouldThrowTaskAssignedNotFoundException() {
        UUID nonExistentTaskId = UUID.randomUUID();

        when(taskAssignedRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());

        assertThrows(TaskAssignedNotFoundException.class, () -> taskAssignedService.getTaskById(nonExistentTaskId));

        verify(taskAssignedRepository).findById(nonExistentTaskId);
    }

    @Test
    void updateStatus_shouldReturnUpdatedTaskAssigned() {
        UUID taskId = UUID.randomUUID();
        String status = "done";

        TaskAssigned existingTaskAssigned = new TaskAssigned(taskId, "Description", TaskAssignedStatus.TO_DO, null, null, null, null);
        TaskAssigned updatedTaskAssigned = new TaskAssigned(taskId, "Description", TaskAssignedStatus.DONE, null, null, null, null);

        when(taskAssignedRepository.findById(taskId)).thenReturn(Optional.of(existingTaskAssigned));
        when(taskAssignedRepository.save(existingTaskAssigned)).thenReturn(updatedTaskAssigned);

        TaskAssigned result = taskAssignedService.updateStatus(taskId, status);

        assertNotNull(result);
        assertEquals(updatedTaskAssigned, result);
        assertEquals(TaskAssignedStatus.DONE, result.getStatus());

        verify(taskAssignedRepository).findById(taskId);
        verify(taskAssignedRepository).save(existingTaskAssigned);
    }

    @Test
    void updateStatus_shouldThrowTaskAssignedNotFoundException() {
        UUID taskId = UUID.randomUUID();
        String status = "done";

        when(taskAssignedRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskAssignedNotFoundException.class, () -> taskAssignedService.updateStatus(taskId, status));

        verify(taskAssignedRepository).findById(taskId);
        verify(taskAssignedRepository, never()).save(any());
    }

    @Test
    public void delete_foundTask_shouldDelet() {
        UUID taskId = UUID.randomUUID();
        TaskAssigned sampleTaskAssigned = new TaskAssigned(taskId, "Task 1", TaskAssignedStatus.DONE);

        when(taskAssignedRepository.findById(taskId)).thenReturn(Optional.of(sampleTaskAssigned));

        taskAssignedService.delete(taskId);

        verify(taskAssignedRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void delete_nonexistentTask_shouldThrowTaskNotFoundExcetion() {
        UUID nonExistingTaskId = UUID.randomUUID();

        when(taskAssignedRepository.findById(nonExistingTaskId)).thenReturn(Optional.empty());

        assertThrows(TaskAssignedNotFoundException.class, () -> taskAssignedService.delete(nonExistingTaskId));

        verify(taskAssignedRepository, times(0)).deleteById(nonExistingTaskId);
    }

    @Test
    public void fetAllTasksForGivenUser() {
        UUID userId = UUID.randomUUID();
        List<TaskAssigned> sampleTasks = Arrays.asList(
                new TaskAssigned(UUID.randomUUID(), "Task 1", TaskAssignedStatus.TO_DO),
                new TaskAssigned(UUID.randomUUID(), "Task 2", TaskAssignedStatus.DONE));

        when(taskAssignedRepository.getAllTasks(userId)).thenReturn(sampleTasks);

        List<TaskAssigned> resultTasks = taskAssignedService.getAllTasksForGivenUser(userId);

        assertEquals(sampleTasks.size(), resultTasks.size());
        assertEquals(sampleTasks.get(0).getDescription(), resultTasks.get(0).getDescription());
        assertEquals(sampleTasks.get(1).getDescription(), resultTasks.get(1).getDescription());

        verify(taskAssignedRepository, times(1)).getAllTasks(userId);
    }

    @Test
    public void getAllTasksForGivenProject() {
        UUID projectId = UUID.randomUUID();
        List<TaskAssigned> sampleTasks = Arrays.asList(
                new TaskAssigned(UUID.randomUUID(), "Task 1", TaskAssignedStatus.NICE_TO_HAVE),
                new TaskAssigned(UUID.randomUUID(), "Task 2", TaskAssignedStatus.IN_PROGRESS));

        when(taskAssignedRepository.getAllTasksForProject(projectId)).thenReturn(sampleTasks);

        List<TaskAssigned> resultTasks = taskAssignedService.getAllTasksForGivenProject(projectId);

        assertEquals(sampleTasks.size(), resultTasks.size());
        assertEquals(sampleTasks.get(0).getDescription(), resultTasks.get(0).getDescription());
        assertEquals(sampleTasks.get(1).getDescription(), resultTasks.get(1).getDescription());

        verify(taskAssignedRepository, times(1)).getAllTasksForProject(projectId);
    }
}
