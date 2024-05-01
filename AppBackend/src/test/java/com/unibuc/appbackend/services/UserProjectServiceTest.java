package com.unibuc.appbackend.services;

import com.unibuc.appbackend.embeddedIds.UserProjectEmbeddedId;
import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.entities.UserProject;
import com.unibuc.appbackend.interfaces.UserProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProjectServiceTest {

    @InjectMocks
    private UserProjectService userProjectService;

    @Mock
    private UserProjectRepository userProjectRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Test
    void createUserProject_shouldReturnCreatedUserProject() {
        UUID userId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();

        User user = new User(userId, "John", "Doe", "john.doe@example.com", "password");
        Project project = new Project(projectId, "Project Name", "Project Description");

        UserProjectEmbeddedId userProjectEmbeddedId = new UserProjectEmbeddedId(projectId, userId);
        UserProject expectedUserProject = new UserProject(userProjectEmbeddedId, user, project);

        when(userService.getUserById(userId)).thenReturn(user);
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(userProjectRepository.save(any(UserProject.class))).thenReturn(expectedUserProject);

        UserProject result = userProjectService.create(userId, projectId);

        assertNotNull(result);
        assertEquals(expectedUserProject.getUserProjectEmbeddedId(), result.getUserProjectEmbeddedId());
        assertEquals(expectedUserProject.getUser(), result.getUser());
        assertEquals(expectedUserProject.getProject(), result.getProject());

        verify(userService).getUserById(userId);
        verify(projectService).getProjectById(projectId);
        verify(userProjectRepository).save(any(UserProject.class));
    }

    @Test
    void getProjectsForGivenUser_shouldReturnProjectsList() {
        UUID userId = UUID.randomUUID();

        Project project1 = new Project(UUID.randomUUID(), "Project 1", "Description 1");
        Project project2 = new Project(UUID.randomUUID(), "Project 2", "Description 2");

        UserProject userProject1 = new UserProject(new UserProjectEmbeddedId(project1.getProjectId(), userId), null, project1);
        UserProject userProject2 = new UserProject(new UserProjectEmbeddedId(project2.getProjectId(), userId), null, project2);

        List<UserProject> userProjects = List.of(userProject1, userProject2);

        when(userProjectRepository.getAllProjects(userId)).thenReturn(userProjects);

        List<Project> result = userProjectService.getProjectsForGivenUser(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));

        verify(userProjectRepository).getAllProjects(userId);
    }
}
