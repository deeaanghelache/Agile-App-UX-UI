package com.unibuc.appbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.appbackend.controllers.ProjectController;
import com.unibuc.appbackend.dtos.ProjectRequest;
import com.unibuc.appbackend.entities.Project;
import com.unibuc.appbackend.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @Test
    public void createProject() throws Exception {
        UUID uuid = UUID.randomUUID();
        ProjectRequest projectRequest = new ProjectRequest("Project1", "Description");
        Project project = new Project(uuid, "Project1", "Description");

        when(projectService.create(any())).thenReturn(project);

        mockMvc.perform(post("/project/createProject")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(projectRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(projectRequest.getDescription()))
                .andExpect(jsonPath("$.name").value(projectRequest.getName()));
    }

    @Test
    public void deleteProject() throws Exception {
        UUID projectId = UUID.randomUUID();

        mockMvc.perform(delete("/project/delete/{id}", projectId))
                .andExpect(status().isOk());

        verify(projectService, times(1)).delete(projectId);
    }

    @Test
    public void getAllProjects() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        List<Project> sampleProjects = Arrays.asList(
                new Project(id1, "Project 1", "Description 1"),
                new Project(id2, "Project 2", "Description 2"));

        when(projectService.getAll()).thenReturn(sampleProjects);

         mockMvc.perform(get("/project/getAllProjects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(sampleProjects.size()))
                 .andExpect(jsonPath("$[0].projectId").value(id1.toString()))
                 .andExpect(jsonPath("$[1].projectId").value(id2.toString()))
                 .andExpect(jsonPath("$[0].name").value("Project 1"))
                 .andExpect(jsonPath("$[1].name").value("Project 2"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[1].description").value("Description 2"));

        verify(projectService, times(1)).getAll();
    }

    @Test
    public void updateProject() throws Exception {
        UUID projectId = UUID.randomUUID();
        String newDescription = "Updated Description";
        Project mockUpdatedProject = new Project(projectId, "Updated Project", newDescription);

        when(projectService.update(projectId, newDescription)).thenReturn(mockUpdatedProject);

        mockMvc.perform(put("/project/update/{id}", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newDescription))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectId").value(mockUpdatedProject.getProjectId().toString()))
                .andExpect(jsonPath("$.name").value(mockUpdatedProject.getName()))
                .andExpect(jsonPath("$.description").value(mockUpdatedProject.getDescription()));

        verify(projectService, times(1)).update(projectId, newDescription);
    }
}
