package com.unibuc.appbackend.services;

import com.unibuc.appbackend.embeddedIds.UserRoleEmbeddedId;
import com.unibuc.appbackend.entities.Role;
import com.unibuc.appbackend.entities.User;
import com.unibuc.appbackend.entities.UserRole;
import com.unibuc.appbackend.enums.RoleName;
import com.unibuc.appbackend.interfaces.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @InjectMocks
    private UserRoleService userRoleService;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RoleService roleService;

    @Test
    void addRoleForUser_shouldSaveUserRole() {
        User user = new User(UUID.randomUUID(), "John", "Doe", "john.doe@example.com", "password");
        Role userRole = new Role(UUID.randomUUID(), RoleName.USER);

        UserRoleEmbeddedId userRoleEmbeddedId = new UserRoleEmbeddedId(userRole.getRoleId(), user.getUserId());
        UserRole expectedUserRole = new UserRole(userRoleEmbeddedId, user, userRole);

        when(roleService.getRoleByName(RoleName.USER)).thenReturn(userRole);

        userRoleService.addRoleForUser(user);

        verify(roleService).getRoleByName(RoleName.USER);
        verify(userRoleRepository).save(expectedUserRole);
    }

    @Test
    public void getAllRolesForGivenUser() {
        UUID userId = UUID.randomUUID();
        List<UserRole> sampleUserRoles = Arrays.asList(
                new UserRole(new UserRoleEmbeddedId(UUID.randomUUID(), userId), new User(userId, null, null, null, null), new Role(UUID.randomUUID(), RoleName.ADMIN)),
                new UserRole(new UserRoleEmbeddedId(UUID.randomUUID(), userId), new User(userId, null, null, null, null), new Role(UUID.randomUUID(), RoleName.SCRUM_MASTER)));

        when(userRoleRepository.queryBy(userId)).thenReturn(sampleUserRoles);

        List<UserRole> resultUserRoles = userRoleService.getAllRolesForGivenUser(userId);

        assertEquals(sampleUserRoles.size(), resultUserRoles.size());
        assertEquals(sampleUserRoles.get(0).getRole().getRoleName(), resultUserRoles.get(0).getRole().getRoleName());
        assertEquals(sampleUserRoles.get(1).getRole().getRoleName(), resultUserRoles.get(1).getRole().getRoleName());

        verify(userRoleRepository, times(1)).queryBy(userId);
    }

    @Test
    public void checkAdminRoleForGivenUser() {
        UUID userId = UUID.randomUUID();
        List<UserRole> sampleUserRoles = Arrays.asList(
                new UserRole(new UserRoleEmbeddedId(UUID.randomUUID(), userId), new User(userId, null, null, null, null), new Role(UUID.randomUUID(), RoleName.ADMIN)),
                new UserRole(new UserRoleEmbeddedId(UUID.randomUUID(), userId), new User(userId, null, null, null, null), new Role(UUID.randomUUID(), RoleName.USER)));

        when(userRoleRepository.queryBy(userId)).thenReturn(sampleUserRoles);

        assertTrue(userRoleService.checkAdminRoleForGivenUser(userId));

        verify(userRoleRepository, times(1)).queryBy(userId);
    }

    @Test
    public void addUserRole() {
        UUID userId = UUID.randomUUID();
        String roleName = "admin";
        RoleName roleNameEnum = RoleName.ADMIN;
        Role sampleRole = new Role(UUID.randomUUID(), roleNameEnum);
        User sampleUser = new User(userId, null, null, null, null);
        UserRole sampleUserRole = new UserRole(new UserRoleEmbeddedId(sampleRole.getRoleId(), userId), sampleUser, sampleRole);

        when(roleService.getRoleByName(roleNameEnum)).thenReturn(sampleRole);
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(sampleUserRole);

        UserRole resultUserRole = userRoleService.addUserRole(userId, roleName);

        assertNotNull(resultUserRole);
        assertEquals(sampleRole.getRoleId(), resultUserRole.getRole().getRoleId());
        assertEquals(userId, resultUserRole.getUser().getUserId());

        verify(userRoleRepository, times(1)).save(any(UserRole.class));
    }

}
