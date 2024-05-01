package com.unibuc.appbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.appbackend.controllers.SprintController;
import com.unibuc.appbackend.dtos.SprintRequest;
import com.unibuc.appbackend.entities.Sprint;
import com.unibuc.appbackend.services.SprintService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.UUID;

@WebMvcTest(controllers = SprintController.class)
public class SprintControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SprintService sprintService;

    @Test
    public void createSprint() throws Exception {
        SprintRequest sprintRequest = new SprintRequest(LocalDate.now());

        when(sprintService.create(any())).thenReturn(new Sprint(UUID.randomUUID(), LocalDate.now()));

        mockMvc.perform(post("/sprint/createSprint")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sprintRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deadline").value(LocalDate.now().toString()));
    }

    @Test
    public void getSprintById() throws Exception {
        UUID sprintId = UUID.randomUUID();
        Sprint mockSprint = new Sprint(sprintId, LocalDate.now(), null);

        when(sprintService.getById(sprintId)).thenReturn(mockSprint);

        mockMvc.perform(get("/sprint/getById/{id}", sprintId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sprintId").value(mockSprint.getSprintId().toString()))
                .andExpect(jsonPath("$.deadline").value(mockSprint.getDeadline().toString()));

        verify(sprintService, times(1)).getById(sprintId);
    }

    @Test
    public void updateSprint() throws Exception {
        UUID sprintId = UUID.randomUUID();
        LocalDate newDeadline = LocalDate.now().plusDays(7);
        Sprint mockUpdatedSprint = new Sprint(sprintId, newDeadline);

        when(sprintService.update(sprintId, newDeadline)).thenReturn(mockUpdatedSprint);

        mockMvc.perform(put("/sprint/update/{id}", sprintId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newDeadline)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sprintId").value(mockUpdatedSprint.getSprintId().toString()))
                .andExpect(jsonPath("$.deadline").value(mockUpdatedSprint.getDeadline().toString()));

        verify(sprintService, times(1)).update(sprintId, newDeadline);
    }

    @Test
    public void deleteSprint() throws Exception {
        UUID sprintId = UUID.randomUUID();

        mockMvc.perform(delete("/sprint/delete/{id}", sprintId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(sprintService, times(1)).delete(sprintId);
    }

}
