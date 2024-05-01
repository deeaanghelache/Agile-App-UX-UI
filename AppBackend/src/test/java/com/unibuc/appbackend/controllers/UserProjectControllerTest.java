package com.unibuc.appbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.appbackend.embeddedIds.UserProjectEmbeddedId;
import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.entities.UserProject;
import com.unibuc.appbackend.services.UserProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserProjectController.class)
public class UserProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserProjectService userProjectService;

    @Test
    public void createUserProject() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();

        User user = new User(userId, "User", "1", "user@gmail.com", "parola");
        Project project = new Project(projectId, "Project1", "Description1");

        UserProjectEmbeddedId userProjectEmbeddedId = new UserProjectEmbeddedId(projectId, userId);
        UserProject userProject = new UserProject(userProjectEmbeddedId, user, project);

        when(userProjectService.create(userId, projectId)).thenReturn(userProject);

        mockMvc.perform(post("/userProject/createUserProject/{userId}/{projectId}", userId, projectId)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userProjectEmbeddedId.projectId").value(userProjectEmbeddedId.getProjectId().toString()))
                .andExpect(jsonPath("$.userProjectEmbeddedId.userId").value(userProjectEmbeddedId.getUserId().toString()));
    }

    @Test
    public void getProjectsForGivenUser() throws Exception {
        UUID userId = UUID.randomUUID();
        List<Project> sampleProjects = Arrays.asList(
                new Project(UUID.randomUUID(), "Project 1", "Description 1"),
                new Project(UUID.randomUUID(), "Project 2", "Description 2"));

        when(userProjectService.getProjectsForGivenUser(userId)).thenReturn(sampleProjects);

        mockMvc.perform(get("/userProject/getAllProjects/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(sampleProjects.size()))
                .andExpect(jsonPath("$[0].name").value("Project 1"))
                .andExpect(jsonPath("$[1].name").value("Project 2"));

        verify(userProjectService, times(1)).getProjectsForGivenUser(userId);
    }
}
