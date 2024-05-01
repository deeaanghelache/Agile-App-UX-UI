package com.unibuc.appbackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unibuc.appbackend.embeddedIds.UserRoleEmbeddedId;
import com.unibuc.appbackend.entities.Role;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.entities.UserRole;
import com.unibuc.appbackend.enums.RoleName;
import com.unibuc.appbackend.services.UserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserRoleController.class)
public class UserRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRoleService userRoleService;

    @Test
    public void checkIfUserIsAdmin() throws Exception {
        UUID userId = UUID.randomUUID();
        boolean isAdmin = true;

        when(userRoleService.checkAdminRoleForGivenUser(userId)).thenReturn(isAdmin);

        mockMvc.perform(get("/userRole/checkAdmin/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(isAdmin));

        verify(userRoleService, times(1)).checkAdminRoleForGivenUser(userId);
    }

    @Test
    public void checkIfUserIsScrumMaster() throws Exception {
        UUID userId = UUID.randomUUID();
        boolean isScrumMaster = true;

        when(userRoleService.checkScrumMasterRoleForGivenUser(userId)).thenReturn(isScrumMaster);

        mockMvc.perform(get("/userRole/checkScrumMaster/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(isScrumMaster));

        verify(userRoleService, times(1)).checkScrumMasterRoleForGivenUser(userId);
    }

    @Test
    public void addUserRole() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        User user = new User(userId, "user", "1", "user@mail.com", "pass");
        Role role = new Role(roleId, RoleName.ADMIN);
        UserRoleEmbeddedId userRoleEmbeddedId = new UserRoleEmbeddedId(roleId, userId);

        UserRole mockUserRole = new UserRole(userRoleEmbeddedId, user, role);

        when(userRoleService.addUserRole(userId, "admin")).thenReturn(mockUserRole);

        mockMvc.perform(post("/userRole/addRole/{id}/{role}", userId, "admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userRoleService, times(1)).addUserRole(userId, "admin");
    }
}
