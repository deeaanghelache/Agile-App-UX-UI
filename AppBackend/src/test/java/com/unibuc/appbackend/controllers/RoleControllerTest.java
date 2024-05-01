package com.unibuc.appbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.appbackend.controllers.RoleController;
import com.unibuc.appbackend.entities.Role;
import com.unibuc.appbackend.enums.RoleName;
import com.unibuc.appbackend.services.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(controllers = RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleService roleService;

    @Test
    public void createRole() throws Exception {
        String roleName = "ADMIN";

        when(roleService.create(any())).thenReturn(new Role(UUID.randomUUID(), RoleName.ADMIN));

        mockMvc.perform(post("/role/createRole/{name}", roleName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value(RoleName.ADMIN.toString()));
    }

    @Test
    public void getRoleById() throws Exception {
        UUID roleId = UUID.randomUUID();

        when(roleService.getRoleById(any())).thenReturn(new Role(roleId, RoleName.ADMIN));

        mockMvc.perform(get("/role/getRole/{id}", roleId).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value(RoleName.ADMIN.toString()));
    }

    @Test
    public void deleteRole() throws Exception {
        UUID roleId = UUID.randomUUID();

        mockMvc.perform(delete("/role/delete/{id}", roleId))
                .andExpect(status().isOk());

        verify(roleService, times(1)).delete(roleId);
    }
}
