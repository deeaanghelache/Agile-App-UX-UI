package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.exceptions.ProjectNotFoundException;
import com.unibuc.appbackend.interfaces.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    void createProject() {
        Project inputProject = new Project();
        inputProject.setName("Sample Project");
        inputProject.setDescription("This is a sample project.");

        Project savedProject = new Project();
        savedProject.setProjectId(UUID.randomUUID());
        savedProject.setName(inputProject.getName());
        savedProject.setDescription(inputProject.getDescription());

        when(projectRepository.save(inputProject)).thenReturn(savedProject);

        Project result = projectService.create(inputProject);

        assertNotNull(result);
        assertEquals(savedProject.getProjectId(), result.getProjectId());
        assertEquals(savedProject.getName(), result.getName());
        assertEquals(savedProject.getDescription(), result.getDescription());

        verify(projectRepository).save(inputProject);
    }

    @Test
    void getProjectById_existingProject_shouldReturnProject() {
        UUID projectId = UUID.randomUUID();
        Project expectedProject = new Project(projectId, "Sample Project", "This is a sample project.");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(expectedProject));

        Project result = projectService.getProjectById(projectId);

        assertNotNull(result);
        assertEquals(expectedProject.getProjectId(), result.getProjectId());
        assertEquals(expectedProject.getName(), result.getName());
        assertEquals(expectedProject.getDescription(), result.getDescription());

        verify(projectRepository).findById(projectId);
    }

    @Test
    void getProjectById_nonexistentProject_shouldThrowProjectNotFoundException() {
        UUID nonExistentProjectId = UUID.randomUUID();

        when(projectRepository.findById(nonExistentProjectId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(nonExistentProjectId));

        verify(projectRepository).findById(nonExistentProjectId);
    }

    @Test
    void delete_existingProject_shouldDeleteProject() {
        UUID projectId = UUID.randomUUID();

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(new Project()));

        projectService.delete(projectId);

        verify(projectRepository).findById(projectId);
        verify(projectRepository).deleteById(projectId);
    }

    @Test
    void delete_nonexistentProject_shouldThrowProjectNotFoundException() {
        UUID nonExistentProjectId = UUID.randomUUID();

        when(projectRepository.findById(nonExistentProjectId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.delete(nonExistentProjectId));

        verify(projectRepository).findById(nonExistentProjectId);
        verifyNoMoreInteractions(projectRepository);
    }

    @Test
    public void getAllProjects() {
        List<Project> sampleProjects = Arrays.asList(
                new Project(UUID.randomUUID(), "Project 1", "Description 1"),
                new Project(UUID.randomUUID(), "Project 2", "Description 2"));

        when(projectRepository.findAll()).thenReturn(sampleProjects);

        List<Project> resultProjects = projectService.getAll();

        assertEquals(sampleProjects.size(), resultProjects.size());
        assertEquals(sampleProjects.get(0).getName(), resultProjects.get(0).getName());
        assertEquals(sampleProjects.get(1).getName(), resultProjects.get(1).getName());

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void update_existingProject_shouldUpdate() {
        UUID projectId = UUID.randomUUID();
        String newDescription = "Updated Description";
        Project sampleProject = new Project(projectId, "Project 1", "Description 1");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(sampleProject));
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        Project updatedProject = projectService.update(projectId, newDescription);

        assertEquals(newDescription, updatedProject.getDescription());

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void update_nonexistentProject_shouldThrowProjectNotFoundException() {
        UUID nonExistingProjectId = UUID.randomUUID();
        String newDescription = "Updated Description";

        when(projectRepository.findById(nonExistingProjectId)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.update(nonExistingProjectId, newDescription));

        verify(projectRepository, times(0)).save(any(Project.class));
    }
}
