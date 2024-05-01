package com.unibuc.appbackend.services;

import com.unibuc.appbackend.entities.Role;
import com.unibuc.appbackend.enums.RoleName;
import com.unibuc.appbackend.exceptions.UserRoleNotFoundException;
import com.unibuc.appbackend.interfaces.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void getRoleById_existingRole_shouldReturnRole() {
        UUID roleId = UUID.randomUUID();
        Role expectedRole = new Role(roleId, RoleName.ADMIN);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(expectedRole));

        Role result = roleService.getRoleById(roleId);

        assertNotNull(result);
        assertEquals(expectedRole.getRoleId(), result.getRoleId());
        assertEquals(expectedRole.getRoleName(), result.getRoleName());

        verify(roleRepository).findById(roleId);
    }

    @Test
    void getRoleById_nonexistentRole_shouldThrowUserRoleNotFoundException() {
        UUID nonExistentRoleId = UUID.randomUUID();

        when(roleRepository.findById(nonExistentRoleId)).thenReturn(Optional.empty());

        assertThrows(UserRoleNotFoundException.class, () -> roleService.getRoleById(nonExistentRoleId));

        verify(roleRepository).findById(nonExistentRoleId);
    }

    @Test
    void getRoleByName_existingRole_shouldReturnRole() {
        RoleName roleName = RoleName.ADMIN;
        Role expectedRole = new Role(UUID.randomUUID(), roleName);

        when(roleRepository.findByRoleName(roleName)).thenReturn(Optional.of(expectedRole));

        Role result = roleService.getRoleByName(roleName);

        assertNotNull(result);
        assertEquals(expectedRole.getRoleId(), result.getRoleId());
        assertEquals(expectedRole.getRoleName(), result.getRoleName());

        verify(roleRepository).findByRoleName(roleName);
    }

    @Test
    void getRoleByName_nonexistentRole_shouldThrowUserRoleNotFoundException() {
        RoleName nonExistentRoleName = RoleName.USER;

        when(roleRepository.findByRoleName(nonExistentRoleName)).thenReturn(Optional.empty());

        assertThrows(UserRoleNotFoundException.class, () -> roleService.getRoleByName(nonExistentRoleName));

        verify(roleRepository).findByRoleName(nonExistentRoleName);
    }

    @Test
    void createRole_validName_shouldReturnCreatedRole() {
        String roleName = "user";
        Role expectedRole = new Role(UUID.randomUUID(), RoleName.USER);

        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);

        Role result = roleService.create(roleName);

        assertNotNull(result);
        assertEquals(expectedRole.getRoleId(), result.getRoleId());
        assertEquals(expectedRole.getRoleName(), result.getRoleName());

        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void delete_existingRole_shouldDeleteRole() throws RoleNotFoundException {
        UUID roleId = UUID.randomUUID();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(new Role()));

        roleService.delete(roleId);

        verify(roleRepository).findById(roleId);
        verify(roleRepository).deleteById(roleId);
    }

    @Test
    void delete_nonexistentRole_shouldThrowRoleNotFoundException() {
        UUID nonExistentRoleId = UUID.randomUUID();

        when(roleRepository.findById(nonExistentRoleId)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.delete(nonExistentRoleId));

        verify(roleRepository).findById(nonExistentRoleId);
        verifyNoMoreInteractions(roleRepository);
    }
}
